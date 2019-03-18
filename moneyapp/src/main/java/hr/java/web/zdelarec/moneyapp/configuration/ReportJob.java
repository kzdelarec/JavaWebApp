package hr.java.web.zdelarec.moneyapp.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import hr.java.web.zdelarec.moneyapp.database.ExpenseRepository;
import hr.java.web.zdelarec.moneyapp.entities.Expense;
import hr.java.web.zdelarec.moneyapp.entities.Report;
import hr.java.web.zdelarec.moneyapp.enumeration.Type;

public class ReportJob extends QuartzJobBean{
	
	@Autowired
	ExpenseRepository eRep;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		ArrayList<Expense> expenses = Lists.newArrayList(eRep.findAll());
		
		Map<Type, List<Expense>> map = expenses.stream().collect(Collectors.groupingBy(Expense::getXy));
		Report report = new Report();
		report.printTitle();
		for (Entry<Type, List<Expense>> entry : map.entrySet()) {
			ArrayList<Expense> exp = (ArrayList<Expense>) entry.getValue();
			String name = entry.getKey().name();
			String sum = exp.stream().mapToDouble(e -> e.getValue().doubleValue()).sum() + "";
			String max = (exp.stream().mapToDouble(e -> e.getValue().doubleValue()).max().toString()).replace("OptionalDouble[", "").replace("]", "");
			String min = (exp.stream().mapToDouble(e -> e.getValue().doubleValue()).min().toString()).replace("OptionalDouble[", "").replace("]", "");
			report.print(name, sum, max, min);
			
		}
		System.out.println("");
		
	}

}
