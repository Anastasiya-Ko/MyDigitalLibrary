package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO Во-первых, отчёт должен иметь красивый вид,
//TODO Во-вторых, ячейки должны автомаштабироваться,

//TODO В-четвёртых, надо сделать отчёт по всем книгам - это, наверное, как-то в цикле должно быть,
//TODO В-пятых, мне надо сделать сохранение отчёта в приложении, а не рабочем столе,
//TODO В-шестых, меня смущает, что если делаешь отчёт один, то больше этим же методом нельзя воспользоваться, потому что ошибка - книга с таким названием уже существует. Приходится её удалять и заново писать отчёт


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceReport {

    BookDataService dataService;

    /**
     * Стили ячеек, используемые в документа
     */
    Map<String, CellStyle> styles;

    public BookServiceReport(BookDataService dataService) {
        this.dataService = dataService;
        styles = new HashMap<>();
    }

    public void createReport(Long bookId) throws IOException {
        File excel = new File("C:\\Users\\lanko\\Desktop\\test.xlsx");
        FileInputStream fis = new FileInputStream(excel);
        //Создаём книгу
        Workbook book = new XSSFWorkbook(fis);
        //Создаём новый лист
        Sheet sheet = book.createSheet("Books");
        // задаем отступ от края листа для печати
        sheet.setMargin(Sheet.TopMargin, 1.4);
        sheet.setMargin(Sheet.LeftMargin, 0.4);
        sheet.setMargin(Sheet.RightMargin, 0.4);
        // устанавливаем ориентацию листа для печати (альбомная)
        sheet.getPrintSetup().setLandscape(true);
        // устанавливаем кол-во страниц на листе
        sheet.setAutobreaks(true);
        // выравнивание по центру листа
        sheet.setHorizontallyCenter(true);
        // перенос рядов на каждый лист
        sheet.setRepeatingRows(CellRangeAddress.valueOf("3"));

        generateStyle(book);

        // Наполнение документа
        // Создаём на листе строку. Нумерация начинается с нуля
        Row rowFirst = sheet.createRow(0);

        // Установка высоты 1 ряда
        rowFirst.setHeight((short) (3 * 300));

        // Наименование столбцов
        int widthCell = 15;
        initCellWidth(sheet, widthCell, rowFirst.createCell(0), "ID книги", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(1), "Название", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(2), "Дата\nпубликации", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(3), "ID автора", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(4), "Имя\nавтора", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(5), "ID читателя", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(6), "Имя\nчитателя", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(7), "Фамилия\nчитателя", styles.get("header"));


        Row rowSecond = sheet.createRow(1);
        //Создаём в строке ячейку
        Cell idBook = rowSecond.createCell(0);
        //Задаём значение ячейке
        idBook.setCellValue(dataService.createReportById(bookId).getBookId());


        Cell title = rowSecond.createCell(1);
        title.setCellValue(dataService.createReportById(bookId).getTitle());

        Cell yearOfPublication = rowSecond.createCell(2);
        DataFormat format = book.createDataFormat();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        yearOfPublication.setCellStyle(dateStyle);
        // Нумерация лет начинается с 1900-го
        yearOfPublication.setCellValue(dataService.createReportById(bookId).getYearOfPublication());


        Cell idAuthor = rowSecond.createCell(3);
        idAuthor.setCellValue(dataService.createReportById(bookId).getAuthorId());

        Cell authorName = rowSecond.createCell(4);
        authorName.setCellValue(dataService.createReportById(bookId).getAuthorName());

        Cell idReader = rowSecond.createCell(5);
        idReader.setCellValue(dataService.createReportById(bookId).getReaderId());

        Cell readerFirstName = rowSecond.createCell(6);
        readerFirstName.setCellValue(dataService.createReportById(bookId).getReaderFirstName());

        Cell readerLastName = rowSecond.createCell(7);
        readerLastName.setCellValue(dataService.createReportById(bookId).getReaderLastName());

        // Отображение границ таблицы
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(new CellRangeAddress(0, sheet.getLastRowNum(), 0, 55), BorderStyle.THIN, BorderExtent.ALL);
        propertyTemplate.applyBorders(sheet);

        // Сохранение документа
        // откройте OutputStream, чтобы сохранить записанные данные в файл Excel
        FileOutputStream os = new FileOutputStream(excel);
        // Записываем всё в файл
        book.write(os);
        // Закройте OutputStream, файл Excel, чтобы предотвратить утечку
        os.close();
        book.close();
        fis.close();
    }

//    public void getBookReportById(Long bookId) throws IOException {
//        File excel = new File("C:\\Users\\lanko\\Desktop\\test.xlsx");
//        FileInputStream fis = new FileInputStream(excel);
//
//        //Создаём книгу
//        Workbook book = new XSSFWorkbook(fis);
//        //Создаём новый лист
//        Sheet sheet = book.createSheet("Books");
//        // Создаём на листе строку. Нумерация начинается с нуля
//        Row row = sheet.createRow(0);
//
//
//        //Создаём в строке ячейку
//        Cell idBook = row.createCell(0);
//        //Задаём значение ячейке
//        idBook.setCellValue(dataService.createReportById(bookId).getBookId());
//
//
//        Cell title = row.createCell(1);
//        title.setCellValue(dataService.createReportById(bookId).getTitle());
//
//        Cell yearOfPublication = row.createCell(2);
//        DataFormat format = book.createDataFormat();
//        CellStyle dateStyle = book.createCellStyle();
//        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
//        yearOfPublication.setCellStyle(dateStyle);
//        // Нумерация лет начинается с 1900-го
//        yearOfPublication.setCellValue(dataService.createReportById(bookId).getYearOfPublication());
//
//
//        Cell idAuthor = row.createCell(3);
//        idAuthor.setCellValue(dataService.createReportById(bookId).getAuthorId());
//
//        Cell authorName = row.createCell(4);
//        authorName.setCellValue(dataService.createReportById(bookId).getAuthorName());
//
//        Cell idReader = row.createCell(5);
//        idReader.setCellValue(dataService.createReportById(bookId).getReaderId());
//
//        Cell readerFirstName = row.createCell(6);
//        readerFirstName.setCellValue(dataService.createReportById(bookId).getReaderFirstName());
//
//        Cell readerLastName = row.createCell(7);
//        readerLastName.setCellValue(dataService.createReportById(bookId).getReaderLastName());
//
//        // Меняем размер столбца
//        sheet.autoSizeColumn(1);
//
//        // откройте OutputStream, чтобы сохранить записанные данные в файл Excel
//        FileOutputStream os = new FileOutputStream(excel);
//        // Записываем всё в файл
//        book.write(os);
//        // Закройте OutputStream, файл Excel, чтобы предотвратить утечку
//        os.close();
//        book.close();
//        fis.close();
//    }

    /**
     * Создание заголовка
     */
//    private void header(Sheet sheet) {
//        // Создание рядов для шапки таблицы
//        Row row0 = sheet.createRow(0);
//
//        // Установка высоты 1 ряда
//        row0.setHeight((short) (3 * 300));
//
//        // Наименование столбцов
//        int widthCell = 15;
//        initCellWidth(sheet, widthCell, row0.createCell(0), "ID книги", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(1), "Название", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(2), "Дата\nпубликации", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(3), "ID автора", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(4), "Имя\nавтора", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(5), "ID читателя", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(6), "Имя\nчитателя", styles.get("header"));
//        initCellWidth(sheet, widthCell, row0.createCell(7), "Фамилия\nчитателя", styles.get("header"));
//    }

    /**
     * Выбор шрифта текста
     */
    private Font font(Workbook book, boolean bold, int fontSize) {
        Font font = book.createFont();
        font.setFontName("Times New Roman");
        font.setBold(bold);
        font.setFontHeight((short) (fontSize * 20));
        return font;
    }

    /**
     * Стиль ячейки
     */
    private CellStyle cellStyle(Workbook book, Font font) {
        CellStyle style = book.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        return style;
    }

    /**
     * Добавление текста в ячейку
     */
    private void initCell(Cell cell, String text, CellStyle cellStyle) {
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);
    }

    /**
     * Добавление текста в ячейку с указанием ширины колонки
     */
    private void initCellWidth(Sheet sheet, int wight, Cell cell, String text, CellStyle cellStyle) {
        sheet.setColumnWidth(cell.getColumnIndex(), wight * 256);
        cell.setCellValue(text);
        cell.setCellStyle(cellStyle);
    }

    /**
     * Создание стилей ячеек, применяемых в документе
     */
    private void generateStyle(Workbook book) {

        // Шапка таблицы
        CellStyle header = cellStyle(book, font(book, false, 12));
        header.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("header", header);

        // Итоговый ряд по всем инвестпроектам LEFT
        CellStyle investLeft = cellStyle(book, font(book, false, 12));
        investLeft.setAlignment(HorizontalAlignment.LEFT);
        investLeft.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        investLeft.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("all-project-total-left", investLeft);

        // Итоговый ряд по всем инвестпроектам CENTER
        CellStyle investCenter = cellStyle(book, font(book, false, 12));
        investCenter.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        investCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("all-project-total-center", investCenter);

        // Итоговый ряд по проекту LEFT
        CellStyle projectLeft = cellStyle(book, font(book, false, 12));
        projectLeft.setAlignment(HorizontalAlignment.LEFT);
        projectLeft.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        projectLeft.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("project-total-left", projectLeft);

        // Итоговый ряд по проекту CENTER
        CellStyle projectCenter = cellStyle(book, font(book, false, 12));
        projectCenter.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        projectCenter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("project-total-center", projectCenter);

        // Базовый - CENTER
        CellStyle basicCenter = cellStyle(book, font(book, false, 12));
        styles.put("basic-center", basicCenter);

        // Базовый - LEFT
        CellStyle basicLeft = cellStyle(book, font(book, false, 12));
        basicLeft.setAlignment(HorizontalAlignment.LEFT);
        styles.put("basic-left", basicLeft);
    }

}
