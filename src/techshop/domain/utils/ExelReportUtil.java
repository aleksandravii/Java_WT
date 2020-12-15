package techshop.domain.utils;

import jxl.Workbook;
import jxl.write.*;
import techshop.domain.beans.OrderBean;
import techshop.domain.entities.User;
import techshop.domain.exceptions.DomainException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Create and sent to clients browser for download .xls report that contain
 * users personal account data. Used JExcel API
 */
public class ExelReportUtil {

	private static final String ORDER_DIRECTORY = "E:\\developing\\eclipse\\SummaryTask4\\WebContent\\WEB-INF\\reports\\";

	private static String filePath;

	private static String userName;

	private static final Logger LOG = Logger.getLogger(ExelReportUtil.class);

	/**
	 * Create .xls file, feel users personal account data and send to users
	 * browser
	 * 
	 * @param response
	 * @param orderList
	 * @param user
	 */
	public static void downloadReport(HttpServletResponse response, List<OrderBean> orderList, User user) {
		LOG.debug("Start creating report");
		try {
			createAccountReport(orderList, user);
			downloadFile(response);
		} catch (WriteException | IOException e) {
			LOG.error("Creating report error", e);
			throw new DomainException(e);
		}
		LOG.debug("Finished creating report");
	}

	private static void createAccountReport(List<OrderBean> orderList, User user) throws WriteException, IOException {
		LOG.trace("Obtained list orders ==> " + orderList);
		LOG.trace("Obtained user ==> " + user);

		userName = user.getFirstName() + "_" + user.getLastName();
		filePath = ORDER_DIRECTORY + userName + "_report.xls";
		
		WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
		WritableSheet sheet = workbook.createSheet("Report", 0);
		WritableCellFormat cFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD);
		cFormat.setFont(font);

		Label label = new Label(0, 0, "Report for " + user.getFirstName() + " " + user.getLastName(), cFormat);
		sheet.addCell(label);

		feelDocument(sheet, cFormat, orderList);

		workbook.write();
		workbook.close();
		LOG.trace("Report has been created. File path ==> " + filePath);
	}

	private static void feelDocument(WritableSheet sheet, WritableCellFormat cFormat, List<OrderBean> orderList)
			throws WriteException {
		addCell(sheet, 0, 2, "Order ID", cFormat);
		addCell(sheet, 1, 2, "date", cFormat);
		addCell(sheet, 2, 2, "price", cFormat);
		addCell(sheet, 3, 2, "status", cFormat);
		addCell(sheet, 4, 2, "Goods in order", cFormat);

		int rowCounter = 3;
		int colCounter = 0;
		List<String> goodsList;
		StringBuilder sb;
		for (OrderBean orderBean : orderList) {
			colCounter = 0;
			addCell(sheet, colCounter++, rowCounter, String.valueOf(orderBean.getId()));
			addCell(sheet, colCounter++, rowCounter, String.valueOf(orderBean.getDate()));
			addCell(sheet, colCounter++, rowCounter, String.valueOf(orderBean.getPrice()));
			addCell(sheet, colCounter++, rowCounter, orderBean.getStatus());
			sb = new StringBuilder();
			goodsList = orderBean.getGoodsNames();
			for (int i = 0; i < goodsList.size(); i++) {
				if (i != goodsList.size() - 1) {
					sb.append(goodsList.get(i)).append(", ");
				} else {
					sb.append(goodsList.get(i));
				}
			}
			addCell(sheet, colCounter, rowCounter, sb.toString());
			rowCounter++;
		}
	}

	private static void addCell(WritableSheet sheet, int col, int row, String value) throws WriteException {
		Label label = new Label(col, row, value);
		sheet.addCell(label);
	}

	private static void addCell(WritableSheet sheet, int col, int row, String value, WritableCellFormat cFormat)
			throws WriteException {
		Label label = new Label(col, row, value, cFormat);
		sheet.addCell(label);
	}

	private static void downloadFile(HttpServletResponse response) throws IOException {
		LOG.trace("Start downloading file");
		String fileName = URLEncoder.encode(userName + "_report.xls", "UTF-8");

		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		File xlsFile = new File(filePath);

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(xlsFile);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
		LOG.trace("Finished downloading file");
	}

}
