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

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure configuration.
 *
 * @since 4.10.0
 */
@ApiStatus.Internal
public final class AdventureConfig {
  /**
   * The directory in which our configuration file lives.
   *
   * @since 4.10.0
   */
  public static final String FILESYSTEM_DIRECTORY_NAME = "config";
  /**
   * The configuration file name.
   *
   * @since 4.10.0
   */
  public static final String FILESYSTEM_FILE_NAME = "adventure.properties";

  /**
   * Option for specifying the default translation locale.
   *
   * @since 4.10.0
   */
  public static final StringOption OPTION_DEFAULT_TRANSLATION_LOCALE = forString("defaultTranslationLocale", null);
  /**
   * Option for specifying whether service load failures are fatal.
   *
   * @since 4.10.0
   */
  public static final BooleanOption OPTION_SERVICE_LOAD_FAILURES_ARE_FATAL = forBoolean("serviceLoadFailuresAreFatal", true);
  /**
   * Option for specifying whether to warn when legacy formatting is detected.
   *
   * @since 4.10.0
   */
  public static final BooleanOption OPTION_TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED = forBoolean("text.warnWhenLegacyFormattingDetected", false);

  private AdventureConfig() {
  }

  /**
   * Creates a new {@code boolean} option.
   *
   * @param name the option name
   * @param defaultValue the default value
   * @return the option
   * @since 4.10.0
   */
  public static @NotNull BooleanOption forBoolean(final @NotNull String name, final boolean defaultValue) {
    return new AdventureConfigImpl.BooleanOptionImpl(name, defaultValue);
  }

  /**
   * Creates a new {@code String} option.
   *
   * @param name the option name
   * @param defaultValue the default value
   * @return the option
   * @since 4.10.0
   */
  public static @NotNull StringOption forString(final @NotNull String name, final String defaultValue) {
    return new AdventureConfigImpl.StringOptionImpl(name, defaultValue);
  }

  /**
   * An option.
   *
   * @since 4.10.0
   */
  public interface Option {
    /**
     * Gets the name.
     *
     * @return the name
     * @since 4.10.0
     */
    @NotNull String name();
  }

  /**
   * A {@code boolean} option.
   *
   * @since 4.10.0
   */
  public interface BooleanOption extends Option {
    /**
     * Gets the value.
     *
     * @return the name
     * @since 4.10.0
     */
    @SuppressWarnings("checkstyle:MethodName")
    boolean getBoolean();
  }

  /**
   * A {@code String} option.
   *
   * @since 4.10.0
   */
  public interface StringOption extends Option {
    /**
     * Gets the value.
     *
     * @return the name
     * @since 4.10.0
     */
    @SuppressWarnings("checkstyle:MethodName")
    @Nullable String getString();
  }
}
