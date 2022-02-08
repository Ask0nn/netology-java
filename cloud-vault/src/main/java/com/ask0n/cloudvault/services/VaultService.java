package com.ask0n.cloudvault.services;

import com.ask0n.cloudvault.entities.User;
import com.ask0n.cloudvault.entities.Vault;
import com.ask0n.cloudvault.exceptions.NotFoundException;
import com.ask0n.cloudvault.repositories.UserRepository;
import com.ask0n.cloudvault.repositories.VaultRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VaultService {
    private final VaultRepository vaultRepository;
    private final UserRepository userRepository;

    public VaultService(VaultRepository vaultRepository, UserRepository userRepository) {
        this.vaultRepository = vaultRepository;
        this.userRepository = userRepository;
    }

    public Vault saveFile(MultipartFile file) throws IOException {
        final Vault vault = new Vault();
        final String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        vault.setUser(user);
        vault.setFilename(file.getOriginalFilename());
        vault.setContentType(file.getContentType());
        vault.setSize(file.getSize());
        vault.setData(file.getBytes());

        return vaultRepository.save(vault);
    }

    public void deleteFile(String filename) {
        final Vault vault = getFile(filename);
        vault.setDeleted(true);
        vaultRepository.save(vault);
    }

    public void updateFilename(String filename, String newName) {
        if (filename.equals(newName)) return;
        final Vault vault = getFile(filename);
        vault.setFilename(newName.trim());
        vaultRepository.save(vault);
    }

    public Vault getFile(String fileName) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));

        return vaultRepository.findByFilenameAndDeletedFalseAndUser_Id(fileName, user.getId())
                .orElseThrow(() -> new NotFoundException("Error input data"));
    }

    public List<Vault> getAll() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return vaultRepository.findAllByUser_IdAndDeletedFalse(user.getId())
                .orElseGet(ArrayList::new);
    }
}
