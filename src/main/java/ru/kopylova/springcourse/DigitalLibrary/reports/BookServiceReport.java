package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//TODO Во-первых, отчёт должен иметь красивый вид,
//TODO Во-вторых, ячейки должны автомаштабироваться,
//TODO В-третьих, я плохо поняла, зачем я реализовала DataReport,потому что могу и так обратиться к бд
//TODO В-четвёртых, надо сделать отчёт по всем книгам - это, наверное, как-то в цикле должно быть,
//TODO В-пятых, мне надо сделать сохранение отчёта в приложении, а не рабочем столе,
//TODO В-шестых, меня смущает, что если делаешь отчёт один, то больше этим же методом нельзя воспользоваться, потому что ошибка - книга с таким названием уже существует. Приходится её удалять и заново писать отчёт
//TODO В-седьмых, не знаю как сделать сохранение объекта в отчёт

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceReport {
    BookDataReport bookDataReport;

    public void getBookReportById(Long bookId) throws IOException {
        File excel = new File("C:\\Users\\lanko\\Desktop\\test.xlsx");
        FileInputStream fis = new FileInputStream(excel);

        //Создаём книгу
        Workbook book = new XSSFWorkbook(fis);
        //Создаём новый лист
        Sheet sheet = book.createSheet("Books");
        // Создаём на листе строку. Нумерация начинается с нуля
        Row row = sheet.createRow(0);


        //Создаём в строке ячейку
        Cell id = row.createCell(0);
        //Задаём значение ячейке
        id.setCellValue(bookDataReport.readBookReportById(bookId).getBookId());


        Cell title = row.createCell(1);
        title.setCellValue(bookDataReport.readBookReportById(bookId).getTitle());

        Cell yearOfPublication = row.createCell(2);
        DataFormat format = book.createDataFormat();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        yearOfPublication.setCellStyle(dateStyle);
        // Нумерация лет начинается с 1900-го
        yearOfPublication.setCellValue(bookDataReport.readBookReportById(bookId).getYearOfPublication());

        // Меняем размер столбца
        sheet.autoSizeColumn(1);

        // откройте OutputStream, чтобы сохранить записанные данные в файл Excel
        FileOutputStream os = new FileOutputStream(excel);
        // Записываем всё в файл
        book.write(os);
        // Закройте OutputStream, файл Excel, чтобы предотвратить утечку
        os.close();
        book.close();
        fis.close();
    }
}
