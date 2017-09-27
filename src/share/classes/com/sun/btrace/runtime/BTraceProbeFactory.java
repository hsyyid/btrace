/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.sun.btrace.runtime;

import com.sun.btrace.DebugSupport;
import com.sun.btrace.SharedSettings;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * A factory class for {@linkplain BTraceProbe} instances
 * @author Jaroslav Bachorik
 */
public final class BTraceProbeFactory {
    private final SharedSettings settings;
    private final DebugSupport debug;
    public final Map<String, Object> patterns;

    public BTraceProbeFactory(SharedSettings settings, Map<String, Object> patterns) {
        this.settings = settings;
        this.debug = new DebugSupport(settings);
        this.patterns = patterns;
//        System.out.println("=== Patterns === " + patterns.size() + " " + this);
//        patterns.forEach((x,y) -> System.out.println(x));
    }

    SharedSettings getSettings() {
        return settings;
    }

    public BTraceProbe createProbe(byte[] code) {
        return new BTraceProbe(this, code);
    }

    public BTraceProbe createProbe(InputStream code) {
        try {
            return new BTraceProbe(this, code);
        } catch (IOException e) {
            if (debug.isDebug()) {
                debug.debug(e);
            }
        }
        return null;
    }
}
