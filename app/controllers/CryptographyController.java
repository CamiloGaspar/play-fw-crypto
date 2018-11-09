package controllers;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class CryptographyController extends Controller {


    public Result decrypt() {

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> pubKey = body.getFile("pubKey");

        // TODO sacar texto y llave pública del request

        try {
            byte[] keyBytes = Files.readAllBytes(pubKey.getFile().toPath());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(spec);

            return ok(views.html.resultado.render("desencriptar", "Acá va el resultado"));
        } catch (Exception e) {
            return internalServerError(e.getMessage());
        }
    }

    public Result encrypt() {
        // TODO sacar texto y llave privada del request
        try {
            // TODO implementar encriptación
            return ok();
        } catch (Exception e) {
            return internalServerError();
        }
    }
}
