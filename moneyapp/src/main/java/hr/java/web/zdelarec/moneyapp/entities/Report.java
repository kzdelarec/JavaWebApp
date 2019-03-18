package hr.java.web.zdelarec.moneyapp.entities;

import java.util.Formatter;

public class Report {
	private double total = 0;
	private Formatter f = new Formatter(System.out);

	public void printTitle() {
		f.format("%15s %15s %15s %15s\n", "Type", "Sum", "Max", "Min");
		f.format("%15s %15s %15s %15s\n", "-----", "-------", "--------", "--------");
	}

	public void print(String type, String sum, String max, String min) {
		f.format("%15.15s %15s %15s %15s\n", type, sum, max, min);
	}

	public void printTotal() {
		f.format("%-15s %5s %10.2f\n", "Tax", "", total*0.06);
		f.format("%-15s %5s %10s\n", "", "", "-----");
		f.format("%-15s %5s %10.2f\n", "Total", "",
		total * 1.06);
	}
}
