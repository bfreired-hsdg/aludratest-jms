/*
 * Copyright (C) 2015 Hamburg Sud and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.service.jms.data;

import org.aludratest.dict.Data;

/**
 * Generic key-value-pair data class.
 * @author Volker Bergmann
 */

public class KeyValueData {

    private String key;
    private Data value;

    /** Default constructor.
     * @param key the key to set
     * @param value the value to set */
    public KeyValueData() {
        this(null, null);
    }

    /** Full constructor.
     * @param key
     * @param value */
    public KeyValueData(String key, Data value) {
        this.key = key;
        this.value = value;
    }

    /** @return the {@link #key} */
    public String getKey() {
        return key;
    }

    /** Sets the {@link #key}.
     * @param key the key to set */
    public void setKey(String key) {
        this.key = key;
    }

    /** @return the {@link #value} */
    public Data getValue() {
        return value;
    }

    /** @param value the {@link #value} */
    public void setValue(Data value) {
        this.value = value;
    }

}
