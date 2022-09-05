package algorithms;

import java.util.*;

import processpackage.Prozess;

public class FCFS {
    private ArrayList<Prozess> processes;
    private ArrayList<GanttChartSection> ganttChartData;
    private PriorityQueue<Prozess> processesPQueue;

    public FCFS(ArrayList<Prozess> processes) {
        this.processes = processes;
        ganttChartData = new ArrayList<>();
        processesPQueue = new PriorityQueue<>();
        FCFS_scheduling();
    }


    public void FCFS_scheduling() {
        //gantt chart implementation
        for (Prozess process : processes)
            processesPQueue.add(new Prozess(process));

        int timeLine = 0;
        Prozess prev = null;
        while (!processesPQueue.isEmpty()) {
            Prozess temp = processesPQueue.poll();
            if (prev == null) {
                timeLine += temp.getArrivalTime();
                ganttChartData.add(
                        new GanttChartSection(temp.getArrivalTime(), timeLine + temp.getBurstTime(),
                                temp.getPName()));
            } else {
                timeLine += prev.getBurstTime();
                ganttChartData.add(new GanttChartSection(timeLine, timeLine + temp.getBurstTime(),
                        temp.getPName()));
            }
            prev = temp;
        }
        //

        int temp;
        int sum = 0;

        for (int i = 0; i < processes.size(); i++) {
            for (int j = i + 1; j < processes.size(); j++) {

                if (processes.get(i).getArrivalTime() > processes.get(j).getArrivalTime()) {

                    temp = processes.get(i).getArrivalTime();
                    processes.get(i).setArrivalTime(processes.get(j).getArrivalTime());
                    processes.get(j).setArrivalTime(temp);

                    temp = processes.get(i).getBurstTime();
                    processes.get(i).setBurstTime(processes.get(j).getBurstTime());
                    processes.get(j).setBurstTime(temp);

                    temp = processes.get(i).getPriority();
                    processes.get(i).setPriority(processes.get(j).getPriority());
                    processes.get(j).setPriority(temp);

                    String Temp = processes.get(i).getPName();
                    processes.get(i).setPName(processes.get(j).getPName());
                    processes.get(j).setPName(Temp);

                }

            }

        }

        for (int i = 0; i < processes.size(); i++) {

            if (i == 0) {

                processes.get(i)
                        .setResponseTime(
                                processes.get(i).getArrivalTime()); // Response time calculation

                processes.get(i)
                        .setTerminationTime(processes.get(i).getArrivalTime() + processes.get(i)
                                .getBurstTime()); // Completion time calculation

            } else if (i == 1) {
                processes.get(i)
                        .setResponseTime(processes.get(i - 1).getBurstTime() - processes.get(i)
                                .getArrivalTime()); // Response time calculation

                processes.get(i)
                        .setTerminationTime(
                                processes.get(i - 1).getTerminationTime() + processes.get(i)
                                        .getBurstTime()); // Completion time calculation

            } else {

                for (int j = 0; j < processes.size() - 1; j++) {

                    sum += processes.get(j).getBurstTime();
                }

                processes.get(i)
                        .setResponseTime(sum - processes.get(i)
                                .getArrivalTime()); // Response time calculation

                sum = 0;

                processes.get(i)
                        .setTerminationTime(
                                processes.get(i - 1).getTerminationTime() + processes.get(i)
                                        .getBurstTime()); // Completion time calculation
            }

            processes.get(i)
                    .setTurnaroundTime(processes.get(i).getTerminationTime() - processes.get(i)
                            .getArrivalTime()); //  Turnaround_time calculation

            processes.get(i)
                    .setWaitingTime(processes.get(i).getTurnaroundTime() - processes.get(i)
                            .getBurstTime()); //Waiting_time calculation

        }
    }


    public float getAverageWaiting() {
        float sum = 0;
        for (int i = 0; i < this.processes.size(); i++) {
            sum += this.processes.get(i).getWaitingTime();
        }
        return sum / this.processes.size();
    }

    public float getAverageTurnaround() {
        float sum = 0;
        for (int i = 0; i < this.processes.size(); i++) {
            sum += this.processes.get(i).getTurnaroundTime();
        }
        return sum / this.processes.size();
    }

    public ArrayList<GanttChartSection> getGanttChartData() {
        return ganttChartData;
    }
}
