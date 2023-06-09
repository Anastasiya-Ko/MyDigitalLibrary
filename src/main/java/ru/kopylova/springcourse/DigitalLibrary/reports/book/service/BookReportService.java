package ru.kopylova.springcourse.DigitalLibrary.reports.book.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.reports.book.models.view.BookDTOReport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис формирования отчёта excel
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookReportService {

    BookDataService bookDataService;

    /**
     * Стили ячеек, используемые в документа
     */
    Map<String, CellStyle> styles;

    public BookReportService(BookDataService bookDataService) {
        this.bookDataService = bookDataService;
        styles = new HashMap<>();
    }

    public byte[] createReportAllBooks() throws IOException {

        List<BookDTOReport> books = bookDataService.createListDTO();

        //Создаём книгу
        Workbook book = new XSSFWorkbook();
        //Создаём новый лист
        Sheet sheet = book.createSheet("Books");
        // задаем отступ от края листа для печати
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
        rows(sheet, books);

        // Отображение границ таблицы
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(new CellRangeAddress(0, sheet.getLastRowNum(), 0, 4), BorderStyle.THIN, BorderExtent.ALL);
        propertyTemplate.applyBorders(sheet);

        //создаём исходящий поток байтов
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
        initCellWidth(sheet, widthCell, rowFirst.createCell(0), "№ п/п", styles.get("header"));
        initCellWidth(sheet, widthCell*2, rowFirst.createCell(1), "Название", styles.get("header"));
        initCellWidth(sheet, widthCell, rowFirst.createCell(2), "Год\nпубликации", styles.get("header"));
        initCellWidth(sheet, widthCell*2, rowFirst.createCell(3), "Автор(ы)", styles.get("header"));
        initCellWidth(sheet, widthCell*2, rowFirst.createCell(4), "Читатель(и)", styles.get("header"));

    }

    private void rows(Sheet sheet, List<BookDTOReport> listBooks) {

        for (int i = 0; i < listBooks.size(); i++) {

            var bookDto = listBooks.get(i);

            Row rowAllBooks = sheet.createRow(i+1);

            initCell(rowAllBooks.createCell(0), "" + (i+1), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(1), bookDto.getTitle(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(2), bookDto.getYearOfPublication(), styles.get("basic-center"));
            initCell(rowAllBooks.createCell(3), bookDto.getAuthorName(), styles.get("basic-center"));

            if(bookDto.getReaderName() == null){
                initCell(rowAllBooks.createCell(4), "Книга свободна!", styles.get("basic-center"));
            }else{
                initCell(rowAllBooks.createCell(4), bookDto.getReaderName(), styles.get("basic-center"));
            }
        }


        //Итоговое количество книг в библиотеке
        Row totalAmountBooks = sheet.createRow(sheet.getLastRowNum()+1);
        sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 3));
        initCell(totalAmountBooks.createCell(0), "Итоговое количество книг в библиотеке:", styles.get("total-project-right"));
        initCell(totalAmountBooks.createCell(4), "" + listBooks.size(), styles.get("total-project-right"));

        //Итоговое количество свободных книг в библиотеке
        Row totalAmountFreeBooks = sheet.createRow(sheet.getLastRowNum()+1);
        sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 3));
        initCell(totalAmountFreeBooks.createCell(0), "Количество свободных книг:", styles.get("total-project-right"));
        initCell(totalAmountFreeBooks.createCell(4),
                "" + listBooks.stream().filter(book -> book.getReaderName()==null).toList().size(), styles.get("total-project-right"));

        //Итоговое количество книг, взятых читателями
        Row totalAmountNotFreeBooks = sheet.createRow(sheet.getLastRowNum()+1);
        sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 3));
        initCell(totalAmountNotFreeBooks.createCell(0), "Количество книг, взятых читателями:", styles.get("total-project-right"));
        initCell(totalAmountNotFreeBooks.createCell(4),
                "" + listBooks.stream().filter(book -> book.getReaderName()!=null).toList().size(), styles.get("total-project-right"));

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

        // Итоговый ряд по проекту RIGHT
        CellStyle projectRight = cellStyle(book, font(book, false, 12));
        projectRight.setAlignment(HorizontalAlignment.RIGHT);
        projectRight.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        projectRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("total-project-right", projectRight);

    }

}
