package com.khesam.certificate.exporter.helper;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateHelper {

    public static X509Certificate readCertificateContent(String pemEncoding)
            throws IOException, CertificateException {
        PEMParser parser = new PEMParser(new StringReader(pemEncoding));

        X509CertificateHolder certHolder = (X509CertificateHolder) parser.readObject();

        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }

    public static X509Certificate readCertificateFile(String filePath)
            throws IOException, CertificateException {
        PEMParser parser = new PEMParser(new FileReader(filePath));

        X509CertificateHolder certHolder = (X509CertificateHolder) parser.readObject();

        return new JcaX509CertificateConverter().getCertificate(certHolder);
    }
}
