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
package net.kyori.adventure.text.minimessage;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import net.kyori.adventure.text.minimessage.transformation.TransformationRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * not public api.
 *
 * @since 4.10.0
 */
final class MiniMessageImpl implements MiniMessage {
  static final Consumer<List<String>> DEFAULT_ERROR_CONSUMER = message -> message.forEach(System.out::println);
  static final UnaryOperator<Component> DEFAULT_COMPACTING_METHOD = Component::compact;

  static final MiniMessage INSTANCE = new MiniMessageImpl(TransformationRegistry.standard(), PlaceholderResolver.empty(), false, null, DEFAULT_ERROR_CONSUMER, DEFAULT_COMPACTING_METHOD);

  private final boolean strict;
  private final Appendable debugOutput;
  private final Consumer<List<String>> parsingErrorMessageConsumer;
  private final UnaryOperator<Component> postProcessor;
  final MiniMessageParser parser;

  MiniMessageImpl(final @NotNull TransformationRegistry registry, final @NotNull PlaceholderResolver placeholderResolver, final boolean strict, final Appendable debugOutput, final @NotNull Consumer<List<String>> parsingErrorMessageConsumer, final @NotNull UnaryOperator<Component> postProcessor) {
    this.parser = new MiniMessageParser(registry, placeholderResolver);
    this.strict = strict;
    this.debugOutput = debugOutput;
    this.parsingErrorMessageConsumer = parsingErrorMessageConsumer;
    this.postProcessor = postProcessor;
  }

  @Override
  public @NotNull Component deserialize(final @NotNull String input) {
    return this.parser.parseFormat(input, this.newContext(input, null));
  }

  @Override
  public @NotNull Component deserialize(final @NotNull String input, final @NotNull PlaceholderResolver placeholderResolver) {
    return this.parser.parseFormat(input, this.newContext(input, requireNonNull(placeholderResolver, "placeholderResolver")));
  }

  @Override
  public @NotNull String serialize(final @NotNull Component component) {
    return MiniMessageSerializer.serialize(component);
  }

  @Override
  public @NotNull String escapeTokens(final @NotNull String input) {
    return this.parser.escapeTokens(input, this.newContext(input, null));
  }

  @Override
  public @NotNull String escapeTokens(final @NotNull String input, final @NotNull PlaceholderResolver placeholders) {
    return this.parser.escapeTokens(input, this.newContext(input, placeholders));
  }

  @Override
  public @NotNull String stripTokens(final @NotNull String input) {
    return this.parser.stripTokens(input, this.newContext(input, null));
  }

  @Override
  public @NotNull String stripTokens(final @NotNull String input, final @NotNull PlaceholderResolver placeholders) {
    return this.parser.stripTokens(input, this.newContext(input, placeholders));
  }

  private @NotNull ContextImpl newContext(final @NotNull String input, final @Nullable PlaceholderResolver resolver) {
    if (resolver == null) {
      return ContextImpl.of(this.strict, this.debugOutput, input, this, PlaceholderResolver.empty(), this.postProcessor);
    } else {
      return ContextImpl.of(this.strict, this.debugOutput, input, this, resolver, this.postProcessor);
    }
  }

  /**
   * not public api.
   *
   * @return huhu.
   * @since 4.10.0
   */
  public @NotNull Consumer<List<String>> parsingErrorMessageConsumer() {
    return this.parsingErrorMessageConsumer;
  }

  @Override
  public @NotNull Builder toBuilder() {
    return new BuilderImpl(this);
  }

  static final class BuilderImpl implements Builder {
    private TransformationRegistry registry = TransformationRegistry.standard();
    private PlaceholderResolver placeholderResolver = null;
    private boolean strict = false;
    private Appendable debug = null;
    private Consumer<List<String>> parsingErrorMessageConsumer = DEFAULT_ERROR_CONSUMER;
    private UnaryOperator<Component> postProcessor = DEFAULT_COMPACTING_METHOD;

    BuilderImpl() {
    }

    BuilderImpl(final MiniMessageImpl serializer) {
      this.registry = serializer.parser.registry;
      this.placeholderResolver = serializer.parser.placeholderResolver;
      this.strict = serializer.strict;
      this.debug = serializer.debugOutput;
      this.parsingErrorMessageConsumer = serializer.parsingErrorMessageConsumer;
    }

    @Override
    public @NotNull Builder transformations(final @NotNull TransformationRegistry transformationRegistry) {
      this.registry = requireNonNull(transformationRegistry, "transformationRegistry");
      return this;
    }

    @Override
    public @NotNull Builder transformations(final @NotNull Consumer<TransformationRegistry.Builder> modifier) {
      final TransformationRegistry.Builder builder = this.registry.toBuilder();
      modifier.accept(builder);
      this.registry = builder.build();
      return this;
    }

    @Override
    public @NotNull Builder placeholderResolver(final @Nullable PlaceholderResolver placeholderResolver) {
      this.placeholderResolver = placeholderResolver;
      return this;
    }

    @Override
    public @NotNull Builder strict(final boolean strict) {
      this.strict = strict;
      return this;
    }

    @Override
    public @NotNull Builder debug(final @Nullable Appendable debugOutput) {
      this.debug = debugOutput;
      return this;
    }

    @Override
    public @NotNull Builder parsingErrorMessageConsumer(final @NotNull Consumer<List<String>> consumer) {
      this.parsingErrorMessageConsumer = requireNonNull(consumer, "consumer");
      return this;
    }

    @Override
    public @NotNull Builder postProcessor(final @NotNull UnaryOperator<Component> postProcessor) {
      this.postProcessor = Objects.requireNonNull(postProcessor, "postProcessor");
      return this;
    }

    @Override
    public @NotNull MiniMessage build() {
      return new MiniMessageImpl(this.registry, this.placeholderResolver == null ? PlaceholderResolver.empty() : this.placeholderResolver, this.strict, this.debug, this.parsingErrorMessageConsumer, this.postProcessor);
    }
  }
}
