package sprint7;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class B {

    /*
    Дано количество учебных занятий, проходящих в одной аудитории. Для каждого из них указано время начала и конца.
    Нужно составить расписание, в соответствии с которым в классе можно будет провести как можно больше занятий.

    Если возможно несколько оптимальных вариантов, то выведите любой. Возможно одновременное проведение более
    чем одного занятия нулевой длительности.
     */

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int meetingsNumber = readInt(reader);
            List<Meeting> meetings = getMeetings(meetingsNumber, reader);

            List<Meeting> schedule = createSchedule(meetings);

            writeResult(writer, schedule);
        }
    }

    private static List<Meeting> createSchedule(List<Meeting> meetings) {
        Meeting previousMeeting = meetings.get(0);

        ArrayList<Meeting> schedule = new ArrayList<>();
        schedule.add(previousMeeting);
        for (int i = 1; i < meetings.size(); i++) {
            Meeting meeting = meetings.get(i);
            if (meeting.startHour * 60 + meeting.startMinute >=
                    previousMeeting.finishHour * 60 + previousMeeting.finishMinute) {
                previousMeeting = meeting;
                schedule.add(meeting);
            }
        }
        return schedule;
    }

    private static int readInt(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private static List<Meeting> getMeetings(int meetingsNumber, BufferedReader reader) throws IOException {
        List<Meeting> meetings = new ArrayList<>(meetingsNumber);
        for (int i = 0; i < meetingsNumber; i++) {
            String string = reader.readLine();
            String[] split = string.split(" ");

            int startHour = 0;
            int startMinute = 0;
            int finishHour = 0;
            int finishMinute = 0;
            if (split[0].contains(".")) {
                String[] start = split[0].split("\\.");
                startHour = Integer.parseInt(start[0]);
                startMinute = Integer.parseInt(start[1]);
            } else {
                startHour = Integer.parseInt(split[0]);
            }

            if (split[1].contains(".")) {
                String[] finish = split[1].split("\\.");
                finishHour = Integer.parseInt(finish[0]);
                finishMinute = Integer.parseInt(finish[1]);
            } else {
                finishHour = Integer.parseInt(split[1]);
            }

            meetings.add(new Meeting(startHour, startMinute, finishHour, finishMinute));
        }
        meetings.sort(Meeting.COMPARATOR);
        return meetings;
    }

    private static void writeResult(BufferedWriter writer, List<Meeting> schedule) throws IOException {
        writer.write(String.valueOf(schedule.size()));
        writer.newLine();
        for (Meeting meeting : schedule) {
            writer.write(meeting.printTime());
            writer.newLine();
        }
    }

    private static class Meeting {

        public static final Comparator<Meeting> COMPARATOR = Comparator
                .comparing(Meeting::getFinishHour)
                .thenComparing(Meeting::getFinishMinute)
                .thenComparing(Meeting::getStartHour)
                .thenComparing(Meeting::getStartMinute);

        private final int startHour;
        private final int startMinute;
        private final int finishHour;
        private final int finishMinute;

        public Meeting(int startHour, int startMinute, int finishHour, int finishMinute) {
            this.startHour = startHour;
            this.startMinute = startMinute;
            this.finishHour = finishHour;
            this.finishMinute = finishMinute;
        }

        public int getStartHour() {
            return startHour;
        }

        public int getStartMinute() {
            return startMinute;
        }

        public int getFinishHour() {
            return finishHour;
        }

        public int getFinishMinute() {
            return finishMinute;
        }

        public String printTime() {
            StringBuilder builder = new StringBuilder();
            builder.append(startHour);
            if (startMinute != 0) {
                builder.append(".")
                        .append(startMinute);
            }
            builder.append(" ")
                    .append(finishHour);
            if (finishMinute != 0) {
                builder.append(".")
                        .append(finishMinute);
            }
            return builder.toString();
        }
    }
}
