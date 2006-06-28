/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.runtime;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletContext;

import org.seasar.framework.util.FileInputStreamUtil;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.jsf.util.FileSystemTraversal;

/**
 * @author manhole
 */
public class FileSystemTaglibManagerImpl extends AbstractTaglibManager {

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void init() {
        init("/WEB-INF/");
    }

    public void init(String path) {
        File root = new File(servletContext.getRealPath(path));
        init0(root);
    }

    private void init0(File root) {
        FileSystemTraversal.Handler handler = new FileSystemTraversal.Handler() {
            public void acceptFile(File file) {
                if (file.getName().endsWith(".tld")) {
                    FileInputStream is = null;
                    try {
                        is = FileInputStreamUtil.create(file);
                        scanTld(is);
                    } finally {
                        if (is != null) {
                            InputStreamUtil.close(is);
                        }
                    }
                }
            }

            public void acceptDirectory(File file) {
            }
        };
        FileSystemTraversal.traverse(root, handler);
    }

}
