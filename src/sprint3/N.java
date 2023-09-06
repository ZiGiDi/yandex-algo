package sprint3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class N {


//    Алла захотела, чтобы у неё под окном были узкие клумбы с тюльпанам. На схеме земельного участка клумбы обозначаются просто горизонтальными отрезками,
//    лежащими на одной прямой. Для ландшафтных работ было нанято n садовников. Каждый из них обрабатывал какой-то отрезок на схеме. Процесс был организован
//    не очень хорошо, иногда один и тот же отрезок или его часть могли быть обработаны сразу несколькими садовниками. Таким образом, отрезки, обрабатываемые двумя разными
//    садовниками, сливаются в один. Непрерывный обработанный отрезок затем станет клумбой. Нужно определить границы будущих клумб.

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int count = readInt(reader);
            List<FlowerBed> flowerBeds = readFlowerBedList(count, reader);

            List<FlowerBed> result = findCommonAreas(flowerBeds);

            for (FlowerBed element : result) {
                writer.write(element.toString());
                writer.newLine();
            }
        }
    }

    private static List<FlowerBed> findCommonAreas(List<FlowerBed> flowerBeds) {
        if (flowerBeds.size() == 1) {
            return flowerBeds;
        }
        List<FlowerBed> left = findCommonAreas(flowerBeds.subList(0, flowerBeds.size() / 2));
        List<FlowerBed> right = findCommonAreas(flowerBeds.subList(flowerBeds.size() / 2, flowerBeds.size()));

        List<FlowerBed> result = new ArrayList<>(flowerBeds.size());

        int l = 0, r = 0, k = 0;
        while (l < left.size() && r < right.size()) {
            FlowerBed leftElement = left.get(l);
            FlowerBed rightElement = right.get(r);
            if (leftElement.isCross(rightElement)) {
                left.set(l, leftElement.unit(rightElement));
                r++;
            } else if (leftElement.isBefore(rightElement)) {
                if (k > 0 && result.get(k - 1).isCross(leftElement)) {
                    result.set(k - 1, result.get(k - 1).unit(leftElement));
                } else {
                    result.add(leftElement);
                    k++;
                }
                l++;
            } else {
                if (k > 0 && result.get(k - 1).isCross(rightElement)) {
                    result.set(k - 1, result.get(k - 1).unit(rightElement));
                } else {
                    result.add(rightElement);
                    k++;
                }
                r++;
            }
        }

        while (l < left.size()) {
            FlowerBed leftElement = left.get(l);
            if (!result.isEmpty() && leftElement.isCross(result.get(k - 1))) {
                result.set(k - 1, result.get(k - 1).unit(leftElement));
            } else {
                result.add(leftElement);   // перенеси оставшиеся элементы left в result
                k++;
            }
            l++;
        }

        while (r < right.size()) {
            FlowerBed rightElement = right.get(r);
            if (!result.isEmpty() && rightElement.isCross(result.get(k - 1))) {
                result.set(k - 1, result.get(k - 1).unit(rightElement));
            } else {
                result.add(rightElement);   // перенеси оставшиеся элементы left в result
                k++;
            }
            r++;
        }
        return result;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<FlowerBed> readFlowerBedList(int count, BufferedReader reader) throws IOException {
        ArrayList<FlowerBed> arrayList = new ArrayList<>(count);
        while (reader.ready()) {
            String[] s = reader.readLine().split(" ");
            arrayList.add(new FlowerBed(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
        }
        return arrayList;
    }

    private static class FlowerBed {
        private int begin;
        private int end;

        public FlowerBed(int begin, int and) {
            this.begin = begin;
            this.end = and;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }

        public boolean isCross(FlowerBed other) {
            return begin >= other.getBegin() && begin <= other.getEnd()
                    || end >= other.getBegin() && end <= other.getEnd()
                    || other.getBegin() >= begin && other.getBegin() <= end
                    || other.getEnd() >= begin && other.getEnd() <= end;
        }

        public boolean isBefore(FlowerBed other) {
            return end < other.begin;
        }

        public FlowerBed unit(FlowerBed other) {
            return new FlowerBed(Math.min(begin, other.getBegin()),
                    Math.max(end, other.getEnd()));

        }


        @Override
        public String toString() {
            return begin + " " + end;
        }
    }
}
