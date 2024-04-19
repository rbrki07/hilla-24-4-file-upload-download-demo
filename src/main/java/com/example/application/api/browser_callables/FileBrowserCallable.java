package com.example.application.api.browser_callables;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.crud.CrudRepositoryService;
import com.example.application.entities.File;
import com.example.application.repositories.FileRepository;

@BrowserCallable
@AnonymousAllowed
public class FileBrowserCallable extends CrudRepositoryService<File, Long, FileRepository> {
}
