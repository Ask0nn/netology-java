package com.ask0n.cloudvault.controllers;

import com.ask0n.cloudvault.entities.Vault;
import com.ask0n.cloudvault.services.VaultService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class VaultController {
    private final VaultService vaultService;

    public VaultController(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @GetMapping("list")
    public List<Vault> getList(@RequestParam int limit) {
        if (limit < 0) throw new IllegalArgumentException("Limit must be greater than or equal to 0");
        final List<Vault> vault = vaultService.getAll();
        final int size = vault.size();
        return vault.subList(0, limit > size ? size : limit);
    }

    @PostMapping("file")
    public void uploadFile(@RequestBody MultipartFile file) throws IOException {
        vaultService.saveFile(file);
    }

    @DeleteMapping("file")
    public void deleteFile(@RequestParam String filename) {
        vaultService.deleteFile(filename);
    }

    @GetMapping("file")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {
        Vault vault = vaultService.getFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + vault.getFilename())
                .contentType(MediaType.valueOf(vault.getContentType()))
                .body(vault.getData());
    }

    @PutMapping(value = "file", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateFilename(@RequestParam String filename, @RequestBody Map<String, String> name) {
        final Optional<String> key = name.keySet().stream().filter(n -> n.contains("name")).findAny();
        if (!key.isPresent()) throw new IllegalArgumentException("New filename cannot be null");
        String newName = name.get(key.get());
        vaultService.updateFilename(filename, newName);
    }
}
