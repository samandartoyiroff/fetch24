package uz.tenzorsoft.fetch24.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.tenzorsoft.fetch24.domain.Role;
import uz.tenzorsoft.fetch24.domain.User;
import uz.tenzorsoft.fetch24.model.RoleName;
import uz.tenzorsoft.fetch24.repository.RoleRepository;
import uz.tenzorsoft.fetch24.repository.UserRepository;




@Component
@RequiredArgsConstructor
public class GenerateData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {

        if (ddl.equals("create")) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName(RoleName.ROLE_ADMIN);
            roleAdmin.setDescription("Admin");
            roleRepository.save(roleAdmin);

            Role roleEditor = new Role();
            roleEditor.setRoleName(RoleName.ROLE_EDITOR);
            roleEditor.setDescription("Editor");
            roleRepository.save(roleEditor);

            Role roleAuthor = new Role();
            roleAuthor.setRoleName(RoleName.ROLE_AUTHOR);
            roleAuthor.setDescription("Author");
            roleRepository.save(roleAuthor);

            User admin = new User();
            admin.setUsername("admin01");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setEmail("samandartoyirov020@gmail.com");
            admin.setRole(roleAdmin);
            userRepository.save(admin);

            User editor = new User();
            editor.setUsername("editor01");
            editor.setPassword(passwordEncoder.encode("123456"));
            editor.setEmail("samandartoyirov021@gmail.com");
            editor.setRole(roleEditor);
            userRepository.save(editor);

            User author = new User();
            author.setUsername("author01");
            author.setPassword(passwordEncoder.encode("123456"));
            author.setEmail("samandartoyirov022@gmail.com");
            author.setRole(roleAuthor);
            userRepository.save(author);
        }

    }
}
