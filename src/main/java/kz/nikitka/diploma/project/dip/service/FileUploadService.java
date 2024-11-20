package kz.nikitka.diploma.project.dip.service;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import kz.nikitka.diploma.project.dip.model.Ingredient;
import kz.nikitka.diploma.project.dip.model.Recipe;
import kz.nikitka.diploma.project.dip.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${uploadPath}")
    private String uploadPath;
    @Value("${loadPath}")
    private String loadPath;


    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    public void uploadPicture(MultipartFile file) {
        try {
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                byte bytes[] = file.getBytes();
                String filePath = loadPath;
                String fileName = DigestUtils.sha1Hex(userService.getCurrentUser().getId() + "asd") + ".jpg";
                Path path = Paths.get(filePath + fileName);
                Files.write(path, bytes);

                User user = userService.getCurrentUser();
                user.setAva(fileName);
                userService.updateUser(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public byte[] getAva(String url ) throws IOException {
        String path = "static/images/";
        String image = null;
        if (userService.getCurrentUser().getAva() != null && userService.getCurrentUser().getAva().equals(url)) {
            image = path +  userService.getCurrentUser().getAva();
        }
        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(image);
            in = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(path + "111.jpg");
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }

    public void uploadImage(MultipartFile pic,Long id) {
        try {
            if (pic.getContentType().equals("image/jpeg") || pic.getContentType().equals("image/png")) {
                byte bytes[] = pic.getBytes();
                String filePath = loadPath;
                String fileName = DigestUtils.sha1Hex(recipeService.getRecipe(id) + "asd") + ".jpg";
                Path path = Paths.get(filePath + fileName);
                Files.write(path, bytes);

                Recipe recipe = recipeService.getRecipe(id);
                recipe.setImage(fileName);
                recipeService.updateRecipe(recipe);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public byte[] getImage(String url,Long id ) throws IOException {
        String path = "static/images/";
        String image = null;
        if (recipeService.getRecipe(id).getImage() != null && recipeService.getRecipe(id).getImage().equals(url)) {
            image = path +  recipeService.getRecipe(id).getImage();
        }
        InputStream in;

        try {
            ClassPathResource resource = new ClassPathResource(image);
            in = resource.getInputStream();
        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(path + "111.jpg");
            in = resource.getInputStream();
        }
        return IOUtils.toByteArray(in);
    }
}
