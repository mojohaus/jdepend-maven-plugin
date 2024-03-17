package org.codehaus.mojo.jdepend.objects;

/*
 * #%L
 * JDepend Maven Plugin
 * %%
 * Copyright (C) 2006 - 2014 Codehaus
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author Who ever this implemented first.
 */
public class JDPackage {
    /* Elements */
    private Stats stats;

    private String packageName;

    private List<String> abstractClasses;

    private List<String> concreteClasses;

    private List<String> dependsUpon;

    private List<String> usedBy;

    /**
     * Creates a new instance of JDPackage.
     */
    public JDPackage() {}

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String name) {
        this.packageName = name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<String> getAbstractClasses() {
        if (abstractClasses == null) {
            abstractClasses = new ArrayList<>();
        }
        return abstractClasses;
    }

    public void addAbstractClasses(String object) {
        getAbstractClasses().add(object);
    }

    public List<String> getConcreteClasses() {
        if (concreteClasses == null) {
            concreteClasses = new ArrayList<>();
        }
        return concreteClasses;
    }

    public void addConcreteClasses(String object) {
        getConcreteClasses().add(object);
    }

    public List<String> getDependsUpon() {
        if (dependsUpon == null) {
            dependsUpon = new ArrayList<>();
        }
        return dependsUpon;
    }

    public void addDependsUpon(String object) {
        getDependsUpon().add(object);
    }

    public void addUsedBy(String object) {
        getUsedBy().add(object);
    }

    public List<String> getUsedBy() {
        if (usedBy == null) {
            usedBy = new ArrayList<>();
        }
        return usedBy;
    }
}
