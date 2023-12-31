// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service/poc_lora.proto

package com.helium.grpc;

/**
 * Protobuf type {@code helium.poc_lora.invalid_details}
 */
public final class invalid_details extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helium.poc_lora.invalid_details)
    invalid_detailsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use invalid_details.newBuilder() to construct.
  private invalid_details(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private invalid_details() {
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new invalid_details();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return PocLoRa.internal_static_helium_poc_lora_invalid_details_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return PocLoRa.internal_static_helium_poc_lora_invalid_details_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            invalid_details.class, Builder.class);
  }

  private int dataCase_ = 0;
  @SuppressWarnings("serial")
  private Object data_;
  public enum DataCase
      implements com.google.protobuf.Internal.EnumLite,
          InternalOneOfEnum {
    DENYLIST_TAG(1),
    DATA_NOT_SET(0);
    private final int value;
    private DataCase(int value) {
      this.value = value;
    }
    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @Deprecated
    public static DataCase valueOf(int value) {
      return forNumber(value);
    }

    public static DataCase forNumber(int value) {
      switch (value) {
        case 1: return DENYLIST_TAG;
        case 0: return DATA_NOT_SET;
        default: return null;
      }
    }
    public int getNumber() {
      return this.value;
    }
  };

  public DataCase
  getDataCase() {
    return DataCase.forNumber(
        dataCase_);
  }

  public static final int DENYLIST_TAG_FIELD_NUMBER = 1;
  /**
   * <code>string denylist_tag = 1;</code>
   * @return Whether the denylistTag field is set.
   */
  public boolean hasDenylistTag() {
    return dataCase_ == 1;
  }
  /**
   * <code>string denylist_tag = 1;</code>
   * @return The denylistTag.
   */
  public String getDenylistTag() {
    Object ref = "";
    if (dataCase_ == 1) {
      ref = data_;
    }
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      if (dataCase_ == 1) {
        data_ = s;
      }
      return s;
    }
  }
  /**
   * <code>string denylist_tag = 1;</code>
   * @return The bytes for denylistTag.
   */
  public com.google.protobuf.ByteString
      getDenylistTagBytes() {
    Object ref = "";
    if (dataCase_ == 1) {
      ref = data_;
    }
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      if (dataCase_ == 1) {
        data_ = b;
      }
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (dataCase_ == 1) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, data_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (dataCase_ == 1) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, data_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof invalid_details)) {
      return super.equals(obj);
    }
    invalid_details other = (invalid_details) obj;

    if (!getDataCase().equals(other.getDataCase())) return false;
    switch (dataCase_) {
      case 1:
        if (!getDenylistTag()
            .equals(other.getDenylistTag())) return false;
        break;
      case 0:
      default:
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    switch (dataCase_) {
      case 1:
        hash = (37 * hash) + DENYLIST_TAG_FIELD_NUMBER;
        hash = (53 * hash) + getDenylistTag().hashCode();
        break;
      case 0:
      default:
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static invalid_details parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_details parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_details parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_details parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_details parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_details parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_details parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static invalid_details parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static invalid_details parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static invalid_details parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static invalid_details parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static invalid_details parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(invalid_details prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code helium.poc_lora.invalid_details}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.poc_lora.invalid_details)
      invalid_detailsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PocLoRa.internal_static_helium_poc_lora_invalid_details_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PocLoRa.internal_static_helium_poc_lora_invalid_details_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              invalid_details.class, Builder.class);
    }

    // Construct using com.helium.grpc.invalid_details.newBuilder()
    private Builder() {

    }

    private Builder(
        BuilderParent parent) {
      super(parent);

    }
    @Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      dataCase_ = 0;
      data_ = null;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return PocLoRa.internal_static_helium_poc_lora_invalid_details_descriptor;
    }

    @Override
    public invalid_details getDefaultInstanceForType() {
      return invalid_details.getDefaultInstance();
    }

    @Override
    public invalid_details build() {
      invalid_details result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public invalid_details buildPartial() {
      invalid_details result = new invalid_details(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      buildPartialOneofs(result);
      onBuilt();
      return result;
    }

    private void buildPartial0(invalid_details result) {
      int from_bitField0_ = bitField0_;
    }

    private void buildPartialOneofs(invalid_details result) {
      result.dataCase_ = dataCase_;
      result.data_ = this.data_;
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof invalid_details) {
        return mergeFrom((invalid_details)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(invalid_details other) {
      if (other == invalid_details.getDefaultInstance()) return this;
      switch (other.getDataCase()) {
        case DENYLIST_TAG: {
          dataCase_ = 1;
          data_ = other.data_;
          onChanged();
          break;
        }
        case DATA_NOT_SET: {
          break;
        }
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              String s = input.readStringRequireUtf8();
              dataCase_ = 1;
              data_ = s;
              break;
            } // case 10
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int dataCase_ = 0;
    private Object data_;
    public DataCase
        getDataCase() {
      return DataCase.forNumber(
          dataCase_);
    }

    public Builder clearData() {
      dataCase_ = 0;
      data_ = null;
      onChanged();
      return this;
    }

    private int bitField0_;

    /**
     * <code>string denylist_tag = 1;</code>
     * @return Whether the denylistTag field is set.
     */
    @Override
    public boolean hasDenylistTag() {
      return dataCase_ == 1;
    }
    /**
     * <code>string denylist_tag = 1;</code>
     * @return The denylistTag.
     */
    @Override
    public String getDenylistTag() {
      Object ref = "";
      if (dataCase_ == 1) {
        ref = data_;
      }
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (dataCase_ == 1) {
          data_ = s;
        }
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string denylist_tag = 1;</code>
     * @return The bytes for denylistTag.
     */
    @Override
    public com.google.protobuf.ByteString
        getDenylistTagBytes() {
      Object ref = "";
      if (dataCase_ == 1) {
        ref = data_;
      }
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        if (dataCase_ == 1) {
          data_ = b;
        }
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string denylist_tag = 1;</code>
     * @param value The denylistTag to set.
     * @return This builder for chaining.
     */
    public Builder setDenylistTag(
        String value) {
      if (value == null) { throw new NullPointerException(); }
      dataCase_ = 1;
      data_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string denylist_tag = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearDenylistTag() {
      if (dataCase_ == 1) {
        dataCase_ = 0;
        data_ = null;
        onChanged();
      }
      return this;
    }
    /**
     * <code>string denylist_tag = 1;</code>
     * @param value The bytes for denylistTag to set.
     * @return This builder for chaining.
     */
    public Builder setDenylistTagBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      dataCase_ = 1;
      data_ = value;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:helium.poc_lora.invalid_details)
  }

  // @@protoc_insertion_point(class_scope:helium.poc_lora.invalid_details)
  private static final invalid_details DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new invalid_details();
  }

  public static invalid_details getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<invalid_details>
      PARSER = new com.google.protobuf.AbstractParser<invalid_details>() {
    @Override
    public invalid_details parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<invalid_details> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<invalid_details> getParserForType() {
    return PARSER;
  }

  @Override
  public invalid_details getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

