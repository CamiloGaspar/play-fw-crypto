package controllers;

import controllers.AsymmetricCryptography;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.ConfiguracionPropertiesLoader;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class CryptographyController extends Controller {


    public Result decrypt() {

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> pubKey = body.getFile("pubKey");

        String texto = body.asFormUrlEncoded().get("texto")[0];
        if (texto == null || texto.equals("")) {
            return ok(views.html.error.render("No se incluyó texto para desencriptar"));
        }

        try {
            AsymmetricCryptography ac = new AsymmetricCryptography();
            byte[] keyBytes = Files.readAllBytes(pubKey.getFile().toPath());

            if (keyBytes == null || keyBytes.length == 0) {
                return ok(views.html.error.render("No se incluyó archivo con la llave publica"));
            }
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(spec);


            String textoDecrypt = ac.decryptText(texto, publicKey);

            return ok(views.html.resultado.render("desencriptar", textoDecrypt));

        } catch (Exception e) {
            return internalServerError(e.getMessage());
        }
    }

    public Result encrypt() {

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        String texto = body.asFormUrlEncoded().get("texto")[0];

        if (texto == null || texto.equals("")) {
            return ok(views.html.error.render("No se incluyó texto para encriptar"));
        }
        try {
            AsymmetricCryptography ac = new AsymmetricCryptography();
            String rutaPrivKey = ConfiguracionPropertiesLoader.getInstance().getValorPropiedad("privateKeyFile");
            PrivateKey privateKey = ac.getPrivate(rutaPrivKey);
            String textoEncrypt = ac.encryptText(texto, privateKey);

            return ok(views.html.resultado.render("encriptar", textoEncrypt));
        } catch (Exception e) {
            return internalServerError();
        }
    }
}
