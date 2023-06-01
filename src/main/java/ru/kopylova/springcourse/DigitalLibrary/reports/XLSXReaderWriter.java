package ru.kopylova.springcourse.DigitalLibrary.reports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class XLSXReaderWriter {

    public static void main(String[] args) {

        try {
            File excel = new File("C:\\Users\\lanko\\Desktop\\test.xlsx");
            FileInputStream fis = new FileInputStream(excel);

            //Находит экземпляр рабочей книги для файла XLSX
            XSSFWorkbook book = new XSSFWorkbook(fis);

            //Возвращает первый лист из рабочей книги XLSX
            XSSFSheet sheet = book.getSheetAt(0);

            //Получаем итератор для всех строк текущего листа
            Iterator<Row> itr = sheet.iterator();

//            // Итерация по файлу Excel в Java
//            while (itr.hasNext()) {
//                Row row = itr.next();
//
//                // Итерация по каждому столбцу файла Excel
//                Iterator<Cell> cellIterator = row.cellIterator();
//                while (cellIterator.hasNext()) {
//
//                    Cell cell = cellIterator.next();
//
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_STRING:
//                            System.out.print(cell.getStringCellValue() + "\t");
//                            break;
//                        case Cell.CELL_TYPE_NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t");
//                            break;
//                        case Cell.CELL_TYPE_BOOLEAN:
//                            System.out.print(cell.getBooleanCellValue() + "\t");
//                            break;
//                        default:
//
//                    }
//                }
//                System.out.println(" ");
//            }

            // запись данных в файл XLSX
            // Теперь давайте запишем некоторые данные в наш XLSX-файл
            Map<String, Object[]> newData = new HashMap<String, Object[]>();
            newData.put("7", new Object[] { 7d, "Анастасия", "75K", "SALES",
                    "Rupert" });
            newData.put("8", new Object[] { 8d, "Александр", "85K", "SALES",
                    "Rupert" });
            newData.put("9", new Object[] { 9d, "Dave", "90K", "SALES",
                    "Rupert" });

            // Настроено на повторение и добавление строк в XLS-файл
            Set<String> newRows = newData.keySet();

            // получить номер последней строки для добавления новых данных
            int rownum = sheet.getLastRowNum();

            for (String key : newRows) {
                // Создание новой строки в существующем листе XLSX
                Row row = sheet.createRow(rownum++);
                Object[] objArr = newData.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }
                }
            }

            // откройте OutputStream, чтобы сохранить записанные данные в файл Excel
            FileOutputStream os = new FileOutputStream(excel);
            book.write(os);
            System.out.println("Запись в файл Excel завершена ...");

            // Закройте книгу, OutputStream и файл Excel, чтобы предотвратить утечку
            os.close();
            book.close();
            fis.close();

        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}