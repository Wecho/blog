package com.wecho.core.qingstor;

import com.qingstor.sdk.config.EvnContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.QingStor;
import com.qingstor.sdk.service.Types;

import java.io.File;
import java.util.List;

public class TestQingStor {
    public static void main(String[] args) throws QSException {
        EvnContext evn = new EvnContext("NOQPQKIUWIFZWPSIFHTM", "2Iy8yuAkXNoDaVYaMXFfhfnpAxOqKRyXSHdId3Dj");
        QingStor storService = new QingStor(evn,"sh1a");

        System.out.println(evn.getHost());
        QingStor.ListBucketsOutput listOutput = storService.listBuckets(null);
        List<Types.BucketModel> bucketModels= listOutput.getBuckets();
        Bucket bucket2 = new Bucket(evn,"sh1a","ul-ecsms-pdf-0x01");


        //System.out.println(listOutput.getBuckets().size());
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        File f = new File("D:\\tools\\123.pdf");
        input.setContentType("application/pdf; charset=utf-8");
        input.setContentLength(f.length());
        input.setBodyInputFile(f);
        Bucket.PutObjectOutput out = bucket2.putObject("testPutObject", input);
        System.out.println(out.getMessage());
    }
}