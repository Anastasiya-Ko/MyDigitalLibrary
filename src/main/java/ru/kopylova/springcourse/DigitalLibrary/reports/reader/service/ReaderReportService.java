package ru.kopylova.springcourse.DigitalLibrary.reports.reader.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.kopylova.springcourse.DigitalLibrary.reports.reader.models.view.ReaderDTOReport;

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
public class ReaderReportService {
    ReaderDataService readerDataService;

    /**
     * Стили ячеек, используемые в документа
     */
    Map<String, CellStyle> styles;

    public ReaderReportService(ReaderDataService readerDataService) {
        this.readerDataService = readerDataService;
        styles = new HashMap<>();
    }

    public byte[] createReportReaderGroupAge() throws IOException {

        //Создаём книгу
        Workbook book = new XSSFWorkbook();
        //Создаём новый лист
        Sheet sheet = book.createSheet("Readers");
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
        rows(sheet);

        // Отображение границ таблицы
        PropertyTemplate propertyTemplate = new PropertyTemplate();
        propertyTemplate.drawBorders(new CellRangeAddress(0, sheet.getLastRowNum(), 0, 3), BorderStyle.THIN, BorderExtent.ALL);
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
        Row row0 = sheet.createRow(0);

        // Установка высоты 1 ряда
        row0.setHeight((short) (3 * 300));

        // Наименование столбцов
        int widthCell = 20;
        initCellWidth(sheet, widthCell / 2, row0.createCell(0), "№ читателя", styles.get("header"));
        initCellWidth(sheet, widthCell, row0.createCell(1), "Имя", styles.get("header"));
        initCellWidth(sheet, widthCell, row0.createCell(2), "Фамилия", styles.get("header"));
        initCellWidth(sheet, widthCell / 2, row0.createCell(3), "Возраст, лет", styles.get("header"));

    }

    private void rows(Sheet sheet) {

        Map<String, List<ReaderDTOReport>> readers = readerDataService.readerGroupAge();

        for (Map.Entry<String, List<ReaderDTOReport>> map : readers.entrySet()) {
            //Заполнение шапки группы читателей
            sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum() + 1, sheet.getLastRowNum() + 1, 0, 3));
            Row groupReaders = sheet.createRow(sheet.getLastRowNum() + 1);
            groupReaders.setHeight((short) (3 * 200));
            initCell(groupReaders.createCell(0), map.getKey(), styles.get("under-header"));

            //Заполнение читателями
            for (ReaderDTOReport dto : map.getValue()) {
                Row reader = sheet.createRow(sheet.getLastRowNum() + 1);

                initCell(reader.createCell(0), dto.getId(), styles.get("basic-center"));
                initCell(reader.createCell(1), dto.getFirstName(), styles.get("basic-center"));
                initCell(reader.createCell(2), dto.getLastName(), styles.get("basic-center"));
                initCell(reader.createCell(3), "" + dto.getAge(), styles.get("basic-center"));

            }

            //Заполнение итогового ряда
            sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum() + 1, sheet.getLastRowNum() + 1, 0, 2));
            Row totalAmountReaderGroup = sheet.createRow(sheet.getLastRowNum() + 1);
            totalAmountReaderGroup.setHeight((short) (2 * 200));
            initCell(totalAmountReaderGroup.createCell(0), "Итоговое количество читателей в группе:", styles.get("total-project-right"));
            initCell(totalAmountReaderGroup.createCell(3), "" + map.getValue().size(), styles.get("total-project-right"));

        }
        //Заполнение итога по всей таблице
        sheet.addMergedRegion(new CellRangeAddress(sheet.getLastRowNum() + 1, sheet.getLastRowNum() + 1, 0, 2));
        Row totalReaders = sheet.createRow(sheet.getLastRowNum() + 1);
        totalReaders.setHeight((short) (3 * 300));
        initCell(totalReaders.createCell(0), "Итоговое количество читателей в группе:", styles.get("total-project-right"));
        initCell(totalReaders.createCell(3), "" + readers.values().stream().mapToInt(List::size).sum(), styles.get("total-project-right"));

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
        header.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("header", header);

        // Подшапка
        CellStyle underHeader = cellStyle(book, font(book, false, 12));
        underHeader.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        underHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("under-header", underHeader);

        // Базовый - CENTER
        CellStyle basicCenter = cellStyle(book, font(book, false, 12));
        styles.put("basic-center", basicCenter);

        // Итоговый ряд по проекту RIGHT
        CellStyle projectRight = cellStyle(book, font(book, false, 12));
        projectRight.setAlignment(HorizontalAlignment.RIGHT);
        projectRight.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        projectRight.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("total-project-right", projectRight);

    }
}
