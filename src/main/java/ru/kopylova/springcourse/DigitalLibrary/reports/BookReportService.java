package ru.kopylova.springcourse.DigitalLibrary.reports;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.models.view.BookDTOReport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO Во-вторых, ячейки должны автомаштабироваться,

//TODO В-четвёртых, надо сделать отчёт по всем книгам - это, наверное, как-то в цикле должно быть,
//TODO В-пятых, мне надо сделать сохранение отчёта в приложении, а не рабочем столе,
//TODO В-шестых, меня смущает, что если делаешь отчёт один, то больше этим же методом нельзя воспользоваться, потому что ошибка - книга с таким названием уже существует. Приходится её удалять и заново писать отчёт
//TODO Зачем два отдельных метода - запись в ячейку с указанием ширины и без?

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookReportService {

    BookDataService dataService;

    /**
     * Стили ячеек, используемые в документа
     */
    Map<String, CellStyle> styles;


    public BookReportService(BookDataService dataService) {
        this.dataService = dataService;
        styles = new HashMap<>();
    }

    public byte[] createReportAllBooks(List<BookDTOReport> listBooks) throws IOException {

        //Создаём книгу
        Workbook book = new XSSFWorkbook();
        //Создаём новый лист
        Sheet sheet = book.createSheet("Book");
        // задаем отступ от края листа для печати
        sheet.setMargin(PageMargin.TOP, 1.4);
        sheet.setMargin(PageMargin.TOP, 1.4);
        sheet.setMargin(PageMargin.LEFT, 0.4);
        sheet.setMargin(PageMargin.RIGHT, 0.4);
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
        header(sheet);
        rows(sheet, listBooks);


        // Отображение границ таблицы
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(new CellRangeAddress(0, sheet.getLastRowNum(), 0, 7), BorderStyle.THIN, BorderExtent.ALL);
        propertyTemplate.applyBorders(sheet);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        book.write(baos);

        return baos.toByteArray();
    }

    /**
     * Создание заголовка
     */
    private void header(Sheet sheet) {
        // Создаём на листе строку. Нумерация начинается с нуля
        Row rowFirst = sheet.createRow(0);

        // Установка высоты 1 ряда
        rowFirst.setHeight((short) (3 * 300));

        // Наименование столбцов
        int widthCell = 15;
        initCellWidth(sheet, widthCell, rowFirst.createCell(0), "№ книги", styles.get("header"));
        initCellWidth(sheet, widthCell*2, rowFirst.createCell(1), "Название", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(2), "Год\nпубликации", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(3), "№ автора", styles.get("header"));
        initCellWidth(sheet, widthCell*2, rowFirst.createCell(4), "Имя\nавтора", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(5), "№ читателя", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(6), "Имя\nчитателя", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(7), "Фамилия\nчитателя", styles.get("header"));
    }

    private void rows(Sheet sheet, List<BookDTOReport> listBooks) {

        for (int i = 0; i < listBooks.size(); i++) {

            var bookDto = listBooks.get(i);

            Row rowAllBooks = sheet.createRow(i+1);

            initCell(rowAllBooks.createCell(0),bookDto.getBookId(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(1), bookDto.getTitle(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(2), bookDto.getYearOfPublication(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(3), bookDto.getAuthorId(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(4), bookDto.getAuthorName(), styles.get("basic-center"));

            if(bookDto.getReaderId() == null){
                sheet.addMergedRegion(new CellRangeAddress(i+1, i+1, 5, 7));
                initCell(rowAllBooks.createCell(5), "Книга свободна!", styles.get("basic-center"));
            }else{
                initCell(rowAllBooks.createCell(5), bookDto.getReaderId(), styles.get("basic-center"));
                initCell(rowAllBooks.createCell(6), bookDto.getReaderFirstName(), styles.get("basic-center"));
                initCell(rowAllBooks.createCell(7), bookDto.getReaderLastName(), styles.get("basic-center"));
            }
        }
    }

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

        // Базовый - CENTER
        CellStyle basicCenter = cellStyle(book, font(book, false, 12));
        styles.put("basic-center", basicCenter);

    }

}
