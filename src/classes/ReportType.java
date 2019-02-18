package classes;

public class ReportType {
	private String day;
	private String entryTime;
	private String exitTime;
	private String timePerDay;
	private String reportType;
	
	public ReportType(String day,String entryTime,String exitTime,String timePerDay,String reportType)
	{
		this.day=day;
		this.entryTime=entryTime;
		this.exitTime=exitTime;
		this.timePerDay=timePerDay;
		this.reportType=reportType;
	}
	public String getDay() {
		return day;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}
	public String getTimePerDay() {
		return timePerDay;
	}
	public String getReportType() {
		return reportType;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "day='"+getDay()+"', enterHour='"+getEntryTime()+"', exitHour='"+getExitTime()+"', sum='"+getTimePerDay()+"', report type='"+getReportType()+"'";
	}
	
	
}
