/*
 * Copyright 2019 Confluent Inc.
 *
 * Licensed under the Confluent Community License (the "License"); you may not use
 * this file except in compliance with the License.  You may obtain a copy of the
 * License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.schema.ksql;

import com.google.errorprone.annotations.Immutable;
import io.confluent.ksql.schema.ksql.types.SqlType;
import java.util.Objects;
import java.util.Optional;

/**
 * A named field within KSQL schema types.
 */
@Immutable
public final class Field {

  private final FieldName name;
  private final SqlType type;

  /**
   * @param name the name of the field.
   * @param type the type of the field.
   * @return the immutable field.
   */
  public static Field of(final String name, final SqlType type) {
    return new Field(FieldName.of(Optional.empty(), name), type);
  }

  /**
   * @param source the name of the source of the field.
   * @param name the name of the field.
   * @param type the type of the field.
   * @return the immutable field.
   */
  public static Field of(final String source, final String name, final SqlType type) {
    return new Field(FieldName.of(Optional.of(source), name), type);
  }

  /**
   * @param name the name of the field.
   * @param type the type of the field.
   * @return the immutable field.
   */
  public static Field of(final FieldName name, final SqlType type) {
    return new Field(name, type);
  }

  private Field(final FieldName name, final SqlType type) {
    this.name = Objects.requireNonNull(name, "name");
    this.type = Objects.requireNonNull(type, "type");
  }

  public FieldName fieldName() {
    return name;
  }

  /**
   * @return the fully qualified field name.
   */
  public String fullName() {
    return name.fullName();
  }

  /**
   * @return the name of the field, without any source / alias.
   */
  public String name() {
    return name.name();
  }

  /**
   * @return the type of the field.
   */
  public SqlType type() {
    return type;
  }

  /**
   * Create a new Field that matches the current, but with the supplied {@code source}.
   *
   * @param source the source to set of the new field.
   * @return the new field.
   */
  public Field withSource(final String source) {
    return new Field(name.withSource(source), type);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Field field = (Field) o;
    return Objects.equals(name, field.name)
        && Objects.equals(type, field.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }

  @Override
  public String toString() {
    return toString(FormatOptions.none());
  }

  public String toString(final FormatOptions formatOptions) {
    return name.toString(formatOptions) + " " + type.toString(formatOptions);
  }
}