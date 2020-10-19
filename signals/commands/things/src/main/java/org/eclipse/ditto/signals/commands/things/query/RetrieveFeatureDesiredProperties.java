/*
 * Copyright (c) 2020 Contributors to the Eclipse Foundation
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
package org.eclipse.ditto.signals.commands.things.query;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonField;
import org.eclipse.ditto.json.JsonFieldDefinition;
import org.eclipse.ditto.json.JsonFieldSelector;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.json.JsonObjectBuilder;
import org.eclipse.ditto.json.JsonPointer;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.json.FieldType;
import org.eclipse.ditto.model.base.json.JsonParsableCommand;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.things.ThingId;
import org.eclipse.ditto.signals.base.WithFeatureId;
import org.eclipse.ditto.signals.commands.base.AbstractCommand;
import org.eclipse.ditto.signals.commands.base.CommandJsonDeserializer;

/**
 * This command retrieves a {@link org.eclipse.ditto.model.things.Feature}'s desired properties.
 *
 * @since 1.4.0
 */
@Immutable
@JsonParsableCommand(typePrefix = RetrieveFeatureDesiredProperties.TYPE_PREFIX, name = RetrieveFeatureDesiredProperties.NAME)
public final class RetrieveFeatureDesiredProperties extends AbstractCommand<RetrieveFeatureDesiredProperties> implements
        ThingQueryCommand<RetrieveFeatureDesiredProperties>, WithFeatureId {

    /**
     * Name of the "Retrieve Feature Desired Properties" command.
     */
    public static final String NAME = "retrieveFeatureDesiredProperties";

    /**
     * Type of this command.
     */
    public static final String TYPE = TYPE_PREFIX + NAME;

    static final JsonFieldDefinition<String> JSON_FEATURE_ID =
            JsonFactory.newStringFieldDefinition("featureId", FieldType.REGULAR, JsonSchemaVersion.V_2);

    static final JsonFieldDefinition<String> JSON_SELECTED_FIELDS =
            JsonFactory.newStringFieldDefinition("selectedFields", FieldType.REGULAR, JsonSchemaVersion.V_2);

    private final ThingId thingId;
    private final String featureId;
    @Nullable private final JsonFieldSelector selectedFields;

    private RetrieveFeatureDesiredProperties(final ThingId thingId,
            final String featureId,
            @Nullable final JsonFieldSelector selectedFields,
            final DittoHeaders dittoHeaders) {

        super(TYPE, dittoHeaders);
        this.thingId = checkNotNull(thingId, "Thing ID");
        this.featureId = checkNotNull(featureId, "Feature ID");
        this.selectedFields = selectedFields;
    }

    /**
     * Returns a Command for retrieving a {@link org.eclipse.ditto.model.things.Feature}'s desired properties on a Thing.
     *
     * @param thingId the {@code Thing}'s ID whose {@code Feature}'s desired properties to retrieve.
     * @param featureId the {@code Feature}'s ID whose desired properties to retrieve.
     * @param dittoHeaders the headers of the command.
     * @return a Command for retrieving the desired properties of the specified Feature.
     * @throws NullPointerException if any argument but {@code thingId} is {@code null}.
     */
    public static RetrieveFeatureDesiredProperties of(final ThingId thingId, final String featureId,
            final DittoHeaders dittoHeaders) {

        return new RetrieveFeatureDesiredProperties(thingId, featureId, null, dittoHeaders);
    }

    /**
     * Returns a Command for retrieving a {@link org.eclipse.ditto.model.things.Feature}'s desired properties on a Thing.
     *
     * @param thingId the {@code Thing}'s ID whose {@code Feature}'s desired properties to retrieve.
     * @param featureId the {@code Feature}'s ID whose desired properties to retrieve.
     * @param selectedFields defines the fields of the JSON representation of the desired properties to retrieve.
     * @param dittoHeaders the headers of the command.
     * @return a Command for retrieving the desired properties of the specified Feature.
     * @throws NullPointerException if {@code featureId} or {@code dittoHeaders} is {@code null}.
     */
    public static RetrieveFeatureDesiredProperties of(final ThingId thingId,
            final String featureId,
            @Nullable final JsonFieldSelector selectedFields,
            final DittoHeaders dittoHeaders) {

        return new RetrieveFeatureDesiredProperties(thingId, featureId, selectedFields, dittoHeaders);
    }

    /**
     * Creates a new {@code RetrieveFeatureDesiredProperties} from a JSON string.
     *
     * @param jsonString the JSON string of which the command is to be created.
     * @param dittoHeaders the headers of the command.
     * @return the command.
     * @throws NullPointerException if any argument is {@code null}.
     * @throws IllegalArgumentException if {@code jsonString} is empty.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonString} was not in the expected
     * format.
     * @throws org.eclipse.ditto.model.things.ThingIdInvalidException if the parsed thing ID did not comply to {@link
     * org.eclipse.ditto.model.base.entity.id.RegexPatterns#ID_REGEX}.
     */
    public static RetrieveFeatureDesiredProperties fromJson(final String jsonString, final DittoHeaders dittoHeaders) {
        return fromJson(JsonFactory.newObject(jsonString), dittoHeaders);
    }

    /**
     * Creates a new {@code RetrieveFeatureDesiredProperties} from a JSON object.
     *
     * @param jsonObject the JSON object of which the command is to be created.
     * @param dittoHeaders the headers of the command.
     * @return the command.
     * @throws NullPointerException if any argument is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonObject} was not in the expected
     * format.
     * @throws org.eclipse.ditto.model.things.ThingIdInvalidException if the parsed thing ID did not comply to {@link
     * org.eclipse.ditto.model.base.entity.id.RegexPatterns#ID_REGEX}.
     */
    public static RetrieveFeatureDesiredProperties fromJson(final JsonObject jsonObject, final DittoHeaders dittoHeaders) {
        return new CommandJsonDeserializer<RetrieveFeatureDesiredProperties>(TYPE, jsonObject).deserialize(() -> {
            final String extractedThingId = jsonObject.getValueOrThrow(ThingQueryCommand.JsonFields.JSON_THING_ID);
            final ThingId thingId = ThingId.of(extractedThingId);
            final String extractedFeatureId = jsonObject.getValueOrThrow(JSON_FEATURE_ID);
            final JsonFieldSelector extractedFieldSelector = jsonObject.getValue(JSON_SELECTED_FIELDS)
                    .map(str -> JsonFactory.newFieldSelector(str, JsonFactory.newParseOptionsBuilder()
                            .withoutUrlDecoding()
                            .build()))
                    .orElse(null);

            return of(thingId, extractedFeatureId, extractedFieldSelector, dittoHeaders);
        });
    }

    @Override
    public Optional<JsonFieldSelector> getSelectedFields() {
        return Optional.ofNullable(selectedFields);
    }

    @Override
    public ThingId getThingEntityId() {
        return thingId;
    }

    @Override
    public String getFeatureId() {
        return featureId;
    }

    @Override
    public JsonPointer getResourcePath() {
        final String path = "/features/" + featureId + "/desiredProperties";
        return JsonPointer.of(path);
    }

    @Override
    protected void appendPayload(final JsonObjectBuilder jsonObjectBuilder, final JsonSchemaVersion schemaVersion,
            final Predicate<JsonField> thePredicate) {

        final Predicate<JsonField> predicate = schemaVersion.and(thePredicate);
        jsonObjectBuilder.set(ThingQueryCommand.JsonFields.JSON_THING_ID, thingId.toString(), predicate);
        jsonObjectBuilder.set(JSON_FEATURE_ID, featureId, predicate);
        if (null != selectedFields) {
            jsonObjectBuilder.set(JSON_SELECTED_FIELDS, selectedFields.toString(), predicate);
        }
    }

    @Override
    public RetrieveFeatureDesiredProperties setDittoHeaders(final DittoHeaders dittoHeaders) {
        return new RetrieveFeatureDesiredProperties(thingId, featureId, selectedFields, dittoHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), thingId, featureId, selectedFields);
    }

    @SuppressWarnings("squid:S1067")
    @Override
    public boolean equals(@Nullable final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final RetrieveFeatureDesiredProperties that = (RetrieveFeatureDesiredProperties) obj;
        return that.canEqual(this) && Objects.equals(thingId, that.thingId)
                && Objects.equals(featureId, that.featureId)
                && Objects.equals(selectedFields, that.selectedFields) && super.equals(that);
    }

    @Override
    protected boolean canEqual(@Nullable final Object other) {
        return other instanceof RetrieveFeatureDesiredProperties;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", thingId=" + thingId + ", featureId=" +
                featureId
                + ", selectedFields=" + selectedFields + "]";
    }

}
