/*
 * Copyright (c) 2019 Contributors to the Eclipse Foundation
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
package org.eclipse.ditto.services.gateway.endpoints.routes.websocket;

import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.services.gateway.streaming.actors.StreamSupervisor;
import org.eclipse.ditto.services.gateway.streaming.actors.SupervisedStream;

/**
 * Provides the means to supervise a particular WebSocket stream.
 */
public interface WebSocketSupervisor extends StreamSupervisor {

    @Override
    void supervise(SupervisedStream supervisedStream, CharSequence connectionCorrelationId, DittoHeaders dittoHeaders);

}
