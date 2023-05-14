package com.ecomerce.Shoes_Shop.Until;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

public class ShoesShopUtil {

    public byte[] compressFile(MultipartFile file) throws IOException, IOException {
        byte[] input = IOUtils.toByteArray(file.getInputStream());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream);
        deflaterOutputStream.write(input);
        deflaterOutputStream.close();

        return outputStream.toByteArray();
    }
}
