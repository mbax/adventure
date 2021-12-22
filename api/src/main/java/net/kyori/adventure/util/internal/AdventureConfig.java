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
import org.jetbrains.annotations.UnknownNullability;

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
  public static final String OPTION_DEFAULT_TRANSLATION_LOCALE = "defaultTranslationLocale";
  /**
   * Option for specifying whether service load failures are fatal.
   *
   * @since 4.10.0
   */
  public static final String OPTION_SERVICE_LOAD_FAILURES_ARE_FATAL = "serviceLoadFailuresAreFatal";
  /**
   * Option for specifying whether to warn when legacy formatting is detected.
   *
   * @since 4.10.0
   */
  public static final String OPTION_TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED = "text.warnWhenLegacyFormattingDetected";

  private AdventureConfig() {
  }

  /**
   * Gets a boolean value.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the boolean value
   * @since 4.10.0
   */
  @SuppressWarnings("checkstyle:MethodName")
  public static boolean getBoolean(final @NotNull String key, final boolean defaultValue) {
    return Boolean.parseBoolean(getString(key, Boolean.toString(defaultValue)));
  }

  /**
   * Gets a string value, or {@code null}.
   *
   * @param key the key
   * @return the string value, or {@code null}
   * @since 4.10.0
   */
  @SuppressWarnings("checkstyle:MethodName")
  public static @Nullable String getString(final @NotNull String key) {
    return getString(key, null);
  }

  /**
   * Gets a string value.
   *
   * @param key the key
   * @param defaultValue the default value
   * @return the string value
   * @since 4.10.0
   */
  @SuppressWarnings("checkstyle:MethodName")
  public static @Nullable String getString(final @NotNull String key, final @Nullable String defaultValue) {
    final String property = String.join(".", "net", "kyori", "adventure", key);
    return AdventureConfigImpl.PROPERTIES.getProperty(key, System.getProperty(property, defaultValue));
  }
}
