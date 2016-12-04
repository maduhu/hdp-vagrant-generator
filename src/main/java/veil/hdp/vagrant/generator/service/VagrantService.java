package veil.hdp.vagrant.generator.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import veil.hdp.vagrant.generator.model.Arguments;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class VagrantService extends AbstractFileService {

    private static final String VAGRANTFILE = "Vagrantfile";

    @Override
    protected void buildFile(Map<String, Object> model, Arguments arguments) {

        String installShellScript = convertTemplateToString(resolver, "templates/common/shell/shell-install.mustache", model);
        String cleanLogsShellScript = convertTemplateToString(resolver, "templates/common/shell/shell-clean-logs.mustache", model);
        String vagrantFile = convertTemplateToString(resolver, "templates/vagrant/vagrantfile.mustache", model);


        final String parentDirectoryName = "out/" + arguments.getFqdn();

        try {
            FileUtils.writeStringToFile(new File(parentDirectoryName, VAGRANTFILE), vagrantFile, StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(new File(parentDirectoryName, "shell-clean-logs.sh"), cleanLogsShellScript, StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(new File(parentDirectoryName, "shell-install.sh"), installShellScript, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }

}
