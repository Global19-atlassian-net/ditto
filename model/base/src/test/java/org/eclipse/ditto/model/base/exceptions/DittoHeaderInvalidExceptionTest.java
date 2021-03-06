/*
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.model.base.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import org.junit.Test;

/**
 * Unit test for {@link DittoHeaderInvalidException}.
 */
public class DittoHeaderInvalidExceptionTest {


    @Test
    public void assertImmutability() {
        assertInstancesOf(DittoHeaderInvalidException.class, areImmutable());
    }

    @Test
    public void buildFromCustomMessage() {
        final String customMessage = "theCustomMessage";

        final DittoHeaderInvalidException headerInvalidException =
                DittoHeaderInvalidException.newCustomMessageBuilder(customMessage)
                        .build();

        assertThat(headerInvalidException.getMessage()).isEqualTo(customMessage);
        assertThat(headerInvalidException.getDescription()).hasValue(
                "Verify that the header has the correct syntax and try again.");
    }

    @Test
    public void buildForInvalidType() {
        final DittoHeaderInvalidException headerInvalidException =
                DittoHeaderInvalidException.newInvalidTypeBuilder("theHeaderName", "theValue", "theExpectedType")
                        .build();

        assertThat(headerInvalidException.getMessage())
                .isEqualTo("The value 'theValue' of the header 'theHeaderName' is not a valid theExpectedType.");
        assertThat(headerInvalidException.getDescription())
                .hasValue("Verify that the value of the header 'theHeaderName' is a valid 'theExpectedType' " +
                        "and try again.");
    }

}
