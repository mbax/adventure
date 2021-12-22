/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.adventure.util.internal;

import java.util.function.Function;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure properties.
 *
 * @since 4.10.0
 */
@ApiStatus.Internal
public final class AdventureProperties {
  /**
   * Option for specifying the default translation locale.
   *
   * @since 4.10.0
   */
  public static final Option<String> DEFAULT_TRANSLATION_LOCALE = option("defaultTranslationLocale", Function.identity());
  /**
   * Option for specifying whether service load failures are fatal.
   *
   * @since 4.10.0
   */
  public static final Option<Boolean> SERVICE_LOAD_FAILURES_ARE_FATAL = option("serviceLoadFailuresAreFatal", Boolean::parseBoolean);
  /**
   * Option for specifying whether to warn when legacy formatting is detected.
   *
   * @since 4.10.0
   */
  public static final Option<Boolean> TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED = option("text.warnWhenLegacyFormattingDetected", Boolean::parseBoolean);

  private AdventureProperties() {
  }

  /**
   * Creates a new option.
   *
   * @param name the option name
   * @param parser the value parser
   * @param <T> the value type
   * @return the option
   * @since 4.10.0
   */
  public static <T> @NotNull Option<T> option(final @NotNull String name, final @NotNull Function<String, T> parser) {
    return new AdventurePropertiesImpl.OptionImpl<>(name, parser);
  }

  /**
   * Gets a boolean value.
   *
   * @param option the option
   * @param defaultValue the default value
   * @return the boolean value
   * @since 4.10.0
   */
  public static boolean booleanValueOf(final @NotNull Option<Boolean> option, final boolean defaultValue) {
    return Boolean.TRUE.equals(valueOf(option, defaultValue));
  }

  /**
   * Gets a boolean value.
   *
   * @param option the option
   * @param defaultValue the default value
   * @return the boolean value
   * @since 4.10.0
   */
  public static <T> @Nullable T valueOf(final @NotNull Option<T> option, final @Nullable T defaultValue) {
    final String key = option.name();
    final String property = String.join(".", "net", "kyori", "adventure", key);
    final String value = System.getProperty(property, AdventurePropertiesImpl.PROPERTIES.getProperty(key));
    return value != null ? ((AdventurePropertiesImpl.OptionImpl<T>) option).parser.apply(value) : defaultValue;
  }

  /**
   * An option.
   *
   * @param <T> the value type
   * @since 4.10.0
   */
  @ApiStatus.Internal
  @ApiStatus.NonExtendable
  @SuppressWarnings("unused")
  public interface Option<T> {
    /**
     * Gets the name.
     *
     * @return the name
     * @since 4.10.0
     */
    @NotNull String name();
  }
}
