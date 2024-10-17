import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String noisyFolder = "src/noisyImages";
        String clearFolder3x3 = "src/clearImages";
        String clearFolder5x5 = "src/clearImages5x5";
        String clearFolder7x7 = "src/clearImages7x7";
        String clearFolder9x9 = "src/clearImages9x9";

        // TROCO O KERNEL DIRETAMENTE NO CÓDIGO E ESCOLHO A PASTA NA HORA DE SALVAR
        int kernelSize = 9;

        File dirNoisy = new File(noisyFolder);
        File[] noisyImages = dirNoisy.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));

        if (noisyImages == null || noisyImages.length == 0) {
            System.out.println("Não foram encontradas imagens na pasta 'noisyImages.");
            return;
        }

        for (File imagemFile : noisyImages) {
            BufferedImage originalImage = ImageIO.read(imagemFile);
            BufferedImage filteredImage = aplicarFiltroMedia(originalImage, kernelSize);

            // IMAGEM SALVA NA PASTA CORRESPONDENTE AO KERNEL ESCOLHIDO LÁ ENCIMA
            File output = new File(clearFolder9x9, imagemFile.getName());
            ImageIO.write(filteredImage, "jpg", output);
            System.out.println("Imagem salva com kernel " + kernelSize + ": " + imagemFile.getPath());

        }
    }
    public static BufferedImage aplicarFiltroMedia(BufferedImage imagem, int kernelSize) {
        int width = imagem.getWidth();
        int height = imagem.getHeight();
        BufferedImage imagemFiltrada = new BufferedImage(width, height, imagem.getType());

        int offset = kernelSize / 2;

        for (int i = offset; i < width - offset; i++) {
            for (int j = offset; j < height - offset; j++) {
                int somaR = 0, somaG = 0, somaB = 0;

                for (int k = -offset; k <= offset; k++) {
                    for (int l = -offset; l <= offset; l++) {
                        Color pixel = new Color(imagem.getRGB(i + k, j + l));
                        somaR += pixel.getRed();
                        somaG += pixel.getGreen();
                        somaB += pixel.getBlue();
                    }
                }
                int numPixels = kernelSize * kernelSize;
                int mediaR = somaR / numPixels;
                int mediaG = somaG / numPixels;
                int mediaB = somaB / numPixels;

                Color novaCor = new Color(mediaR, mediaG, mediaB);
                imagemFiltrada.setRGB(i, j, novaCor.getRGB());
            }
        }
        return imagemFiltrada;
    }
}

