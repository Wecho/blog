package com.wecho.core.test.poi;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPattern;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class RemoveAllText {
    /**
     * Default constructor.
     */
    private RemoveAllText()
    {
        // example class should not be instantiated
    }

    /**
     * This will remove all text from a PDF document.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main(String[] args) throws IOException
    {
        if (args.length == 2)
        {
            usage();
        }
        else
        {
            try (PDDocument document = PDDocument.load(new File("D:\\tools\\123.pdf")))
            {
                if (document.isEncrypted())
                {
                    System.err.println(
                            "Error: Encrypted documents are not supported for this example.");
                    System.exit(1);
                }
                for (PDPage page : document.getPages())
                {
                    List<Object> newTokens = createTokensWithoutText(page);
                    PDStream newContents = new PDStream(document);
                    writeTokensToStream(newContents, newTokens);
                    page.setContents(newContents);
                    processResources(page.getResources());
                }
                document.save("D:\\tools\\123remove.pdf");
            }
        }
    }

    private static void processResources(PDResources resources) throws IOException
    {
        for (COSName name : resources.getXObjectNames())
        {
            PDXObject xobject = resources.getXObject(name);
            if (xobject instanceof PDFormXObject)
            {
                PDFormXObject formXObject = (PDFormXObject) xobject;
                writeTokensToStream(formXObject.getContentStream(),
                        createTokensWithoutText(formXObject));
                processResources(formXObject.getResources());
            }
        }
        for (COSName name : resources.getPatternNames())
        {
            PDAbstractPattern pattern = resources.getPattern(name);
            if (pattern instanceof PDTilingPattern)
            {
                PDTilingPattern tilingPattern = (PDTilingPattern) pattern;
                writeTokensToStream(tilingPattern.getContentStream(),
                        createTokensWithoutText(tilingPattern));
                processResources(tilingPattern.getResources());
            }
        }
    }

    private static void writeTokensToStream(PDStream newContents, List<Object> newTokens) throws IOException
    {
        try (OutputStream out = newContents.createOutputStream(COSName.FLATE_DECODE))
        {
            ContentStreamWriter writer = new ContentStreamWriter(out);
            writer.writeTokens(newTokens);
        }
    }

    private static List<Object> createTokensWithoutText(PDContentStream contentStream) throws IOException
    {
        PDFStreamParser parser = new PDFStreamParser(contentStream);
        Object token = parser.parseNextToken();
        List<Object> newTokens = new ArrayList<>();
        while (token != null)
        {
            //
            if (token instanceof Operator)
            {
                Operator op = (Operator) token;
                if ("TJ".equals(op.getName()) || "Tj".equals(op.getName()) ||
                        "'".equals(op.getName()) || "\"".equals(op.getName()))
                {
                    // remove the one argument to this operator
                    newTokens.remove(newTokens.size() - 1);
                    System.out.println(token);
                    token = parser.parseNextToken();
                    continue;
                }
            }
            newTokens.add(token);
            token = parser.parseNextToken();
        }
        return newTokens;
    }

    /**
     * This will print the usage for this document.
     */
    private static void usage()
    {
        System.err.println(
                "Usage: java " + RemoveAllText.class.getName() + " <input-pdf> <output-pdf>");
    }

}