/*
 * (c) Copyright 2016 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
