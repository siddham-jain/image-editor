import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class BasicImageEditor {

/**
 * Converts a given color BufferedImage to grayscale.
 *
 * @param inputImage The input color BufferedImage to be converted.
 * @return A grayscale BufferedImage with the same dimensions as the inputImage.
 */
   public static BufferedImage convertToGrayscale(BufferedImage inputImage) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    
    // Create a new BufferedImage with grayscale type
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    
    // Copy pixel values from the inputImage to the outputImage
    for(int i = 0; i < height; i++){
        for(int j = 0; j < width; j++){
            outputImage.setRGB(j, i, inputImage.getRGB(j, i));
        }
    }
    
    System.out.println("Conversion to grayscale done!!");
    return outputImage;
}


/**
 * Increases the brightness of a given BufferedImage by a specified percentage.
 *
 * @param inputImage The input BufferedImage to increase brightness.
 * @param increase   The percentage by which to increase brightness (e.g., 10 for 10% increase).
 * @return A new BufferedImage with increased brightness.
 */
public static BufferedImage increaseBrightness(BufferedImage inputImage, int increase) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(j, i));
            
            // Extract the red, green, and blue components
            int red = pixel.getRed();
            int blue = pixel.getGreen();
            int green = pixel.getBlue();
            
            // Increase brightness by the specified percentage
            red = red + (increase * red) / 100;
            green = green + (increase * green) / 100;
            blue = blue + (increase * blue) / 100;
            
            // Ensure that the values are within the valid color range (0-255)
            if (red > 255) red = 255;
            if (blue > 255) blue = 255;
            if (green > 255) green = 255;
            if (red < 0) red = 0;
            if (green < 0) green = 0;
            if (blue < 0) blue = 0;
            
            // Create a new color with the adjusted components
            Color newPixel = new Color(red, blue, green);
            
            // Set the new color in the outputImage
            outputImage.setRGB(j, i, newPixel.getRGB());
        }
    }
    
    System.out.println("Brightness increased!!");
    return outputImage;
}


/**
 * Adjusts the contrast of a given BufferedImage by applying a contrast factor.
 *
 * @param inputImage The input BufferedImage to adjust contrast.
 * @param factor     The contrast factor to apply (e.g., 1.5 for increased contrast).
 * @return A new BufferedImage with adjusted contrast.
 */
public static BufferedImage adjustContrast(BufferedImage inputImage, int factor) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(x, y));

            // Calculate adjusted color components based on the contrast factor
            int red = (int) (factor * (pixel.getRed() - 128) + 128);
            int green = (int) (factor * (pixel.getGreen() - 128) + 128);
            int blue = (int) (factor * (pixel.getBlue() - 128) + 128);
            
            // Ensure that the adjusted values are within the valid color range (0-255)
            red = Math.min(255, Math.max(0, red));
            green = Math.min(255, Math.max(0, green));
            blue = Math.min(255, Math.max(0, blue));

            // Create a new color with the adjusted components
            Color adjustedPixel = new Color(red, green, blue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, adjustedPixel.getRGB());
        }
    }
    
    System.out.println("Contrast adjustment done!!");
    return outputImage;
}


/**
 * Applies a blur effect to a given BufferedImage using a specified blur radius.
 *
 * @param inputImage The input BufferedImage to apply the blur effect.
 * @param blurRadius The radius of the blur effect (larger values result in stronger blur).
 * @return A new BufferedImage with the blur effect applied.
 */
public static BufferedImage applyBlur(BufferedImage inputImage, int blurRadius) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = blurRadius; x < width - blurRadius; x++) {
        for (int y = blurRadius; y < height - blurRadius; y++) {
            int redSum = 0;
            int greenSum = 0;
            int blueSum = 0;

            // Calculate the sum of colors within the blur radius
            for (int i = -blurRadius; i <= blurRadius; i++) {
                for (int j = -blurRadius; j <= blurRadius; j++) {
                    Color pixel = new Color(inputImage.getRGB(x + i, y + j));
                    redSum += pixel.getRed();
                    greenSum += pixel.getGreen();
                    blueSum += pixel.getBlue();
                }
            }

            int numPixels = (2 * blurRadius + 1) * (2 * blurRadius + 1);
            int avgRed = redSum / numPixels;
            int avgGreen = greenSum / numPixels;
            int avgBlue = blueSum / numPixels;

            // Create a new color with the averaged components
            Color blurredPixel = new Color(avgRed, avgGreen, avgBlue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, blurredPixel.getRGB());
        }
    }

    System.out.println("Blur effect applied successfully");
    return outputImage;
}


/**
 * Rotates a given BufferedImage 90 degrees anti-clockwise.
 *
 * @param inputImage The input BufferedImage to rotate.
 * @return A new BufferedImage rotated anti-clockwise by 90 degrees.
 */
public static BufferedImage rotateImageAntiClockwise(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with swapped width and height
    BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);

    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            // Rotate the pixel values anti-clockwise
            outputImage.setRGB(j, width - i - 1, inputImage.getRGB(i, j));
        }
    }
    
    System.out.println("Image rotated successfully!!");
    return outputImage;
}


/**
 * Rotates a given BufferedImage 90 degrees clockwise.
 *
 * @param inputImage The input BufferedImage to rotate.
 * @return A new BufferedImage rotated clockwise by 90 degrees.
 */
public static BufferedImage rotateImageClockwise(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with swapped width and height
    BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);

    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            // Rotate the pixel values clockwise
            outputImage.setRGB(height - j - 1, i, inputImage.getRGB(i, j));
        }
    }
    
    System.out.println("Image rotated successfully!!");
    return outputImage;
}


/**
 * Flips a given BufferedImage vertically.
 *
 * @param inputImage The input BufferedImage to flip vertically.
 * @return A new BufferedImage flipped vertically.
 */
public static BufferedImage flipVertical(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            // Flip the pixel vertically
            outputImage.setRGB(i, j, inputImage.getRGB(i, height - j - 1));
        }
    }
    
    System.out.println("Image flipped vertically successfully");
    return outputImage;
}


/**
 * Flips a given BufferedImage horizontally.
 *
 * @param inputImage The input BufferedImage to flip horizontally.
 * @return A new BufferedImage flipped horizontally.
 */
public static BufferedImage flipHorizontal(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    
    for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
            // Flip the pixel horizontally
            outputImage.setRGB(i, j, inputImage.getRGB(width - i - 1, j));
        }
    }
    
    System.out.println("Image flipped horizontally successfully");
    return outputImage;
}

    
/**
 * Prints the pixel values of a given BufferedImage.
 *
 * @param inputImage The input BufferedImage to print pixel values from.
 * @return The same input BufferedImage.
 */
public static BufferedImage printPixelValues(BufferedImage inputImage) {
    int height = inputImage.getHeight();
    int width = inputImage.getWidth();
    
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(j, i));
            
            // Print the RGB values of the pixel
            System.out.print(pixel.getBlue() + " " + pixel.getGreen() + " " + pixel.getRed() + " ");
        }
        System.out.println();
    }
    
    System.out.println("Pixel values printed successfully");
    
    return inputImage;
}


/**
 * Applies a red light filter to a given BufferedImage by increasing the red channel intensity.
 *
 * @param inputImage The input BufferedImage to apply the red light filter.
 * @param intensity  The intensity of the red light filter to apply.
 * @return A new BufferedImage with the red light filter applied.
 */
public static BufferedImage applyRedLightFilter(BufferedImage inputImage, int intensity) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(x, y));
            
            // Extract the red, green, and blue components
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            
            // Increase the red channel intensity while ensuring it doesn't exceed 255
            red = Math.min(255, red + intensity); 
            
            // Create a new color with the adjusted red channel
            Color filtered = new Color(red, green, blue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, filtered.getRGB());
        }
    }
    
    System.out.println("Red light filter applied successfully");
    
    return outputImage;
}


/**
 * Applies a blue light filter to a given BufferedImage by increasing the blue channel intensity.
 *
 * @param inputImage The input BufferedImage to apply the blue light filter.
 * @param intensity  The intensity of the blue light filter to apply.
 * @return A new BufferedImage with the blue light filter applied.
 */
public static BufferedImage applyBlueLightFilter(BufferedImage inputImage, int intensity) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(x, y));
            
            // Extract the red, green, and blue components
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            
            // Increase the blue channel intensity while ensuring it doesn't exceed 255
            blue = Math.min(255, blue + intensity); 
            
            // Create a new color with the adjusted blue channel
            Color filtered = new Color(red, green, blue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, filtered.getRGB());
        }
    }
    
    System.out.println("Blue light filter applied successfully");
    
    return outputImage;
}


/**
 * Applies a green light filter to a given BufferedImage by increasing the green channel intensity.
 *
 * @param inputImage The input BufferedImage to apply the green light filter.
 * @param intensity  The intensity of the green light filter to apply.
 * @return A new BufferedImage with the green light filter applied.
 */
public static BufferedImage applyGreenLightFilter(BufferedImage inputImage, int intensity) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            // Get the color of the current pixel
            Color pixel = new Color(inputImage.getRGB(x, y));
            
            // Extract the red, green, and blue components
            int red = pixel.getRed();
            int green = pixel.getGreen();
            int blue = pixel.getBlue();
            
            // Increase the green channel intensity while ensuring it doesn't exceed 255
            green = Math.min(255, green + intensity); 
            
            // Create a new color with the adjusted green channel
            Color filtered = new Color(red, green, blue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, filtered.getRGB());
        }
    }
    
    System.out.println("Green light filter applied successfully");
    
    return outputImage;
}


/**
 * Inverts the colors of a given BufferedImage.
 *
 * @param inputImage The input BufferedImage to invert colors.
 * @return A new BufferedImage with inverted colors.
 */
public static BufferedImage invertColors(BufferedImage inputImage) {
    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    
    // Create a new BufferedImage with the same dimensions and type as the inputImage
    BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            // Get the color of the current pixel from the inputImage
            Color pixel = new Color(inputImage.getRGB(x, y));

            // Calculate the inverted color values
            int red = 255 - pixel.getRed();
            int green = 255 - pixel.getGreen();
            int blue = 255 - pixel.getBlue();

            // Create a new color with the inverted components
            Color invertedPixel = new Color(red, green, blue);
            
            // Set the new color in the outputImage
            outputImage.setRGB(x, y, invertedPixel.getRGB());
        }
    }
    
    System.out.println("Colors inverted successfully");
    
    return outputImage;
}

/**
 * The main method for an image processing application that provides various image manipulation options.
 *
 * This method presents a user interface through the console to choose from a menu of image processing functions.
 * It accepts user input for selecting an operation and performs the chosen operation on an input image. The result
 * is then saved as an output image. Supported operations include converting to grayscale, increasing brightness,
 * adjusting contrast, applying blur, rotating, flipping, printing pixel values, applying red/blue/green light filters,
 * cropping, and inverting colors.
 *
 * @param args The command-line arguments (not used in this application).
 */
public static void main(String[] args) {
    // Initialize a scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);
    // Display a menu of available image processing options
        System.out.println("Enter the function you want to execute:");
        System.out.println("01. Convert to grayscale");
        System.out.println("02. Increase brightness");
        System.out.println("03. Adjust contrast");
        System.out.println("04. Apply blur");
        System.out.println("05. Rotate image");
        System.out.println("06. Flip");
        System.out.println("07. Print Pixel values");
        System.out.println("08. Apply red light filter");
        System.out.println("09. Apply blue light filter");
        System.out.println("10. Apply green light filter");
        System.out.println("11. Invert the colours of image");
    // Read the user's choice of operation
        int choice = scanner.nextInt();
    // Define the file path of the input image (update as needed)
        File inputfile = new File("D:/Java/javaStuff/ImageEditor/3.jpeg");

        try {
        // Read the input image from the specified file    
            BufferedImage inputImage = ImageIO.read(inputfile);
            BufferedImage outputImage = null;
        // Perform the selected image processing operation based on user input
            switch (choice) {
            // Each case corresponds to a specific image processing operation
            // Add JavaDoc comments for individual image processing methods
            // to explain their purpose and parameters
                case 1:
                    outputImage = convertToGrayscale(inputImage);
                    break;
                case 2:
                // Prompt for brightness increase factor and validate input
                    System.out.println("Enter increase in Brightness");
                    int inc = scanner.nextInt();
                    if(inc<0){
                    System.out.println("Enter a valid positive factor");
                    break;
                    }
                    outputImage = increaseBrightness(inputImage, inc);
                    break;
                case 3:
                // Prompt for contrast adjustment factor and validate input
                    System.out.println("Enter the amount to adjust contrast");
                    int fac = scanner.nextInt();
                    if(fac<0){
                     System.out.println("Enter a valid positive factor");
                     break;
                    }
                    outputImage = adjustContrast(inputImage, fac);
                    break;
                case 4:
                // Prompt for blur radius
                    System.out.println("Enter blur radius");
                    int blur = scanner.nextInt();
                    outputImage = applyBlur(inputImage, blur);
                    break;
                case 5:
                // Prompt for rotation direction (clockwise or counterclockwise)
                    System.out.println("1. Clockwise");
                    System.out.println("2. AntiClockwise");
                    int chr = scanner.nextInt();
                    if(chr == 1)
                    outputImage = rotateImageClockwise(inputImage);
                    else if(chr == 2)
                    outputImage = rotateImageAntiClockwise(inputImage);
                    else
                    System.out.println("Invalid Choice!!");
                     break;
                case 6:
                // Prompt for flip direction (horizontal or vertical)
                System.out.println("1. Horizontally");
                System.out.println("2. Verically");
                    int chf = scanner.nextInt();
                    if(chf == 2)
                    outputImage = flipVertical(inputImage);
                    else if(chf == 1)
                    outputImage = flipHorizontal(inputImage);
                    else System.out.println("Invalid Choice!!");
                    break;
                case 7:
                    outputImage = printPixelValues(inputImage);
                    break;
                case 8:
                // Prompt for red light filter intensity
                     System.out.println("Enter the intensity of Red light");
                     int intensityRed = scanner.nextInt();
                     outputImage = applyRedLightFilter(inputImage , intensityRed);
                     break;
                case 9:
                // Prompt for blue light filter intensity
                     System.out.println("Enter the intensity of Blue light");
                     int intensityBlue = scanner.nextInt();
                     outputImage = applyBlueLightFilter(inputImage , intensityBlue);
                     break;
                case 10:
                // Prompt for green light filter intensity
                     System.out.println("Enter the intensity of Green light");
                     int intensityGreen = scanner.nextInt();
                     outputImage = applyBlueLightFilter(inputImage , intensityGreen);
                     break;
                case 11:
                     outputImage = invertColors(inputImage);   
                     break;           
                default:
                    System.out.println("Invalid choice!!");
            }
        
            System.out.println("1. You want to apply the changes to the current image");
            System.out.println("2. You want to save the changes to a new image");
            System.out.println("Enter your choice");
            int cho = scanner.nextInt();
            
            // Consume the newline character left in the input buffer
            scanner.nextLine();
            
         if (outputImage != null) {
                if (cho == 1) {
                    // Save the output image to a file if it was processed
                    File outputFile = new File("output.jpg");
                    ImageIO.write(outputImage, "jpg", outputFile);
                    System.out.println("Done!");
                } 
                else if (cho == 2) {
                    // Save the output image to a new file if it was processed
                    System.out.println("Enter the name of the new file with .jpg at the end");
                    String path = scanner.nextLine();
                    File outputFile = new File(path);
                    ImageIO.write(outputImage, "jpg", outputFile);
                    System.out.println("Done!");
                } 
                else {
                    System.out.println("Invalid Choice!");
                }
            }
}
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}

