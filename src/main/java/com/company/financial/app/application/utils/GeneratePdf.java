package com.company.financial.app.application.utils;

import com.company.financial.app.application.service.exception.PersistenceException;
import com.company.financial.app.domain.model.TableReportStructure;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Base64;


public final class GeneratePdf {
    private static final Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC, new BaseColor(Color.black));
    private static final Font cellHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(Color.black));
    private static final Font cellBodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9, new BaseColor(Color.black));

    //  private static final String METRO_LOGO = Objects.requireNonNull(GeneratePdf.class.getResource("/images/logo_metro.png")).toString();

    public static String createReport(TableReportStructure tableReportStructure) {

        ByteArrayOutputStream outFileByteArray = null;

        try {
            Document document = new Document();
            outFileByteArray = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outFileByteArray);

            Chunk chunk = new Chunk(tableReportStructure.getTitleDocument(), titleFont);

            Paragraph paragraph = new Paragraph(chunk);

            Chapter chapter = new Chapter(paragraph, 1);
            chapter.setNumberDepth(0);

            // addImage(chapter);

            PdfPTable table = createTablePdf(tableReportStructure);

            document.open();

            document.setPageSize(PageSize.A4.rotate());
            document.add(chapter);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Fecha: " + "22/05/2024"));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);

            document.close();

            return Base64.getEncoder().encodeToString(outFileByteArray.toByteArray());
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: " + documentException.getMessage());
        }
        return Base64.getEncoder().encodeToString(outFileByteArray.toByteArray());
    }

    private static PdfPTable createTablePdf(TableReportStructure tablePdf) {
        PdfPTable table = new PdfPTable(tablePdf.getTitleTableList().size());
        table.setWidthPercentage(100);

        evaluateNumberOfColumnsAndRows(tablePdf.getContentTableList().size(), tablePdf.getTitleTableList().size());

        tablePdf.getTitleTableList()
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, cellHeaderFont));
                    table.addCell(header);
                });

        tablePdf.getContentTableList()
                .forEach(bodyContent -> {
                            PdfPCell body = new PdfPCell();
                            body.setBackgroundColor(BaseColor.WHITE);
                            body.setHorizontalAlignment(Element.ALIGN_LEFT);
                            body.setBorderWidth(1);
                            body.setPhrase(new Phrase(bodyContent, cellBodyFont));
                            table.addCell(body);
                        }
                );
        return table;
    }

    private static void evaluateNumberOfColumnsAndRows(int sizeRows, int sizeColumns) {
        if (sizeRows % sizeColumns != 0) {
            throw new PersistenceException("La cantidad de columnas no coincide con la cantidad de elementos de la tabla");
        }
    }
}