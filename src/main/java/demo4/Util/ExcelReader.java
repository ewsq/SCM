package demo4.Util;

import demo4.Controller.UploadController;
import demo4.pojo.Student;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReader {

    private final static Logger logger = LoggerFactory.getLogger(ExcelReader.class);
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Student> readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);

            if (!excelFile.exists()) {
                logger.error("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<Student> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.error("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.error("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Student> parseExcel(Workbook workbook) throws ParseException {
        List<Student> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum()+1;
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warn("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum ;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                Student resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warn("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }
        return resultDataList;
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();
                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    private static Date convertCellValueToDate(Cell cell) throws ParseException {
        String cellValue = cell.toString();
        String[] splitAddress = cellValue.split("-");
        String yyyy = cellValue.substring(6,10);
        String MM = cellValue.substring(3,5);
        switch (splitAddress[1]) {
            case "一月":
                splitAddress[1] = "01";
                break;
            case "二月":
                splitAddress[1] = "02";
                break;
            case "三月":
                splitAddress[1] = "03";
                break;
            case "四月":
                splitAddress[1] = "04";
                break;
            case "五月":
                splitAddress[1] = "05";
                break;
            case "六月":
                splitAddress[1] = "06";
                break;
            case "七月":
                splitAddress[1] = "07";
                break;
            case "八月":
                splitAddress[1] = "08";
                break;
            case "九月":
                splitAddress[1] = "09";
                break;
            case "十月":
                splitAddress[1] = "10";
                break;
            case "十一月":
                splitAddress[1] = "11";
                break;
            case "十二月":
                splitAddress[1] = "12";
                break;
        }
        String dateStr = splitAddress[2]+"-"+splitAddress[1]+"-"+splitAddress[0];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dateStr);
        return date;
    }
    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     *
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static Student convertRowToData(Row row) throws ParseException {
        Student resultData = new Student();

        Cell cell;
        int cellNum = 0;
        // 获取id
        cell = row.getCell(cellNum++);
        String idStr = convertCellValueToString(cell);
        resultData.setId(Integer.parseInt(idStr));
        // 获取姓名
        cell = row.getCell(cellNum++);
        String nameStr = convertCellValueToString(cell);
        resultData.setName(nameStr);
        // 获取性别
        cell = row.getCell(cellNum++);
        String sexStr = convertCellValueToString(cell);
        resultData.setSex(sexStr);
        // 获取年龄
        cell = row.getCell(cellNum++);
        String ageStr = convertCellValueToString(cell);
        resultData.setAge(Integer.parseInt(ageStr));
        // 获取生日
        cell = row.getCell(cellNum++);
        Date birthStr = convertCellValueToDate(cell);
        resultData.setBirth(birthStr);

        return resultData;
    }
}

