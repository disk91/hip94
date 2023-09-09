// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service/packet_verifier.proto

package com.helium.grpc;

/**
 * Protobuf type {@code helium.packet_verifier.invalid_packet}
 */
public final class invalid_packet extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helium.packet_verifier.invalid_packet)
    invalid_packetOrBuilder {
private static final long serialVersionUID = 0L;
  // Use invalid_packet.newBuilder() to construct.
  private invalid_packet(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private invalid_packet() {
    gateway_ = com.google.protobuf.ByteString.EMPTY;
    payloadHash_ = com.google.protobuf.ByteString.EMPTY;
    reason_ = 0;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new invalid_packet();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return PacketVerifier.internal_static_helium_packet_verifier_invalid_packet_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return PacketVerifier.internal_static_helium_packet_verifier_invalid_packet_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            invalid_packet.class, Builder.class);
  }

  public static final int PAYLOAD_SIZE_FIELD_NUMBER = 1;
  private int payloadSize_ = 0;
  /**
   * <code>uint32 payload_size = 1;</code>
   * @return The payloadSize.
   */
  @Override
  public int getPayloadSize() {
    return payloadSize_;
  }

  public static final int GATEWAY_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString gateway_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes gateway = 2;</code>
   * @return The gateway.
   */
  @Override
  public com.google.protobuf.ByteString getGateway() {
    return gateway_;
  }

  public static final int PAYLOAD_HASH_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString payloadHash_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>bytes payload_hash = 3;</code>
   * @return The payloadHash.
   */
  @Override
  public com.google.protobuf.ByteString getPayloadHash() {
    return payloadHash_;
  }

  public static final int REASON_FIELD_NUMBER = 4;
  private int reason_ = 0;
  /**
   * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
   * @return The enum numeric value on the wire for reason.
   */
  @Override public int getReasonValue() {
    return reason_;
  }
  /**
   * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
   * @return The reason.
   */
  @Override public invalid_packet_reason getReason() {
    invalid_packet_reason result = invalid_packet_reason.forNumber(reason_);
    return result == null ? invalid_packet_reason.UNRECOGNIZED : result;
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
    if (payloadSize_ != 0) {
      output.writeUInt32(1, payloadSize_);
    }
    if (!gateway_.isEmpty()) {
      output.writeBytes(2, gateway_);
    }
    if (!payloadHash_.isEmpty()) {
      output.writeBytes(3, payloadHash_);
    }
    if (reason_ != invalid_packet_reason.invalid_packet_reason_insufficient_balance.getNumber()) {
      output.writeEnum(4, reason_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (payloadSize_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(1, payloadSize_);
    }
    if (!gateway_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, gateway_);
    }
    if (!payloadHash_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, payloadHash_);
    }
    if (reason_ != invalid_packet_reason.invalid_packet_reason_insufficient_balance.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, reason_);
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
    if (!(obj instanceof invalid_packet)) {
      return super.equals(obj);
    }
    invalid_packet other = (invalid_packet) obj;

    if (getPayloadSize()
        != other.getPayloadSize()) return false;
    if (!getGateway()
        .equals(other.getGateway())) return false;
    if (!getPayloadHash()
        .equals(other.getPayloadHash())) return false;
    if (reason_ != other.reason_) return false;
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
    hash = (37 * hash) + PAYLOAD_SIZE_FIELD_NUMBER;
    hash = (53 * hash) + getPayloadSize();
    hash = (37 * hash) + GATEWAY_FIELD_NUMBER;
    hash = (53 * hash) + getGateway().hashCode();
    hash = (37 * hash) + PAYLOAD_HASH_FIELD_NUMBER;
    hash = (53 * hash) + getPayloadHash().hashCode();
    hash = (37 * hash) + REASON_FIELD_NUMBER;
    hash = (53 * hash) + reason_;
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static invalid_packet parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_packet parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_packet parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_packet parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_packet parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static invalid_packet parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static invalid_packet parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static invalid_packet parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static invalid_packet parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static invalid_packet parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static invalid_packet parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static invalid_packet parseFrom(
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
  public static Builder newBuilder(invalid_packet prototype) {
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
   * Protobuf type {@code helium.packet_verifier.invalid_packet}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.packet_verifier.invalid_packet)
      invalid_packetOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PacketVerifier.internal_static_helium_packet_verifier_invalid_packet_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PacketVerifier.internal_static_helium_packet_verifier_invalid_packet_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              invalid_packet.class, Builder.class);
    }

    // Construct using com.helium.grpc.invalid_packet.newBuilder()
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
      payloadSize_ = 0;
      gateway_ = com.google.protobuf.ByteString.EMPTY;
      payloadHash_ = com.google.protobuf.ByteString.EMPTY;
      reason_ = 0;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return PacketVerifier.internal_static_helium_packet_verifier_invalid_packet_descriptor;
    }

    @Override
    public invalid_packet getDefaultInstanceForType() {
      return invalid_packet.getDefaultInstance();
    }

    @Override
    public invalid_packet build() {
      invalid_packet result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public invalid_packet buildPartial() {
      invalid_packet result = new invalid_packet(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(invalid_packet result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.payloadSize_ = payloadSize_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.gateway_ = gateway_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.payloadHash_ = payloadHash_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.reason_ = reason_;
      }
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof invalid_packet) {
        return mergeFrom((invalid_packet)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(invalid_packet other) {
      if (other == invalid_packet.getDefaultInstance()) return this;
      if (other.getPayloadSize() != 0) {
        setPayloadSize(other.getPayloadSize());
      }
      if (other.getGateway() != com.google.protobuf.ByteString.EMPTY) {
        setGateway(other.getGateway());
      }
      if (other.getPayloadHash() != com.google.protobuf.ByteString.EMPTY) {
        setPayloadHash(other.getPayloadHash());
      }
      if (other.reason_ != 0) {
        setReasonValue(other.getReasonValue());
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
            case 8: {
              payloadSize_ = input.readUInt32();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 18: {
              gateway_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              payloadHash_ = input.readBytes();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              reason_ = input.readEnum();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
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
    private int bitField0_;

    private int payloadSize_ ;
    /**
     * <code>uint32 payload_size = 1;</code>
     * @return The payloadSize.
     */
    @Override
    public int getPayloadSize() {
      return payloadSize_;
    }
    /**
     * <code>uint32 payload_size = 1;</code>
     * @param value The payloadSize to set.
     * @return This builder for chaining.
     */
    public Builder setPayloadSize(int value) {

      payloadSize_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 payload_size = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayloadSize() {
      bitField0_ = (bitField0_ & ~0x00000001);
      payloadSize_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString gateway_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes gateway = 2;</code>
     * @return The gateway.
     */
    @Override
    public com.google.protobuf.ByteString getGateway() {
      return gateway_;
    }
    /**
     * <code>bytes gateway = 2;</code>
     * @param value The gateway to set.
     * @return This builder for chaining.
     */
    public Builder setGateway(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      gateway_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>bytes gateway = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearGateway() {
      bitField0_ = (bitField0_ & ~0x00000002);
      gateway_ = getDefaultInstance().getGateway();
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString payloadHash_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes payload_hash = 3;</code>
     * @return The payloadHash.
     */
    @Override
    public com.google.protobuf.ByteString getPayloadHash() {
      return payloadHash_;
    }
    /**
     * <code>bytes payload_hash = 3;</code>
     * @param value The payloadHash to set.
     * @return This builder for chaining.
     */
    public Builder setPayloadHash(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      payloadHash_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>bytes payload_hash = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayloadHash() {
      bitField0_ = (bitField0_ & ~0x00000004);
      payloadHash_ = getDefaultInstance().getPayloadHash();
      onChanged();
      return this;
    }

    private int reason_ = 0;
    /**
     * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
     * @return The enum numeric value on the wire for reason.
     */
    @Override public int getReasonValue() {
      return reason_;
    }
    /**
     * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
     * @param value The enum numeric value on the wire for reason to set.
     * @return This builder for chaining.
     */
    public Builder setReasonValue(int value) {
      reason_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
     * @return The reason.
     */
    @Override
    public invalid_packet_reason getReason() {
      invalid_packet_reason result = invalid_packet_reason.forNumber(reason_);
      return result == null ? invalid_packet_reason.UNRECOGNIZED : result;
    }
    /**
     * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
     * @param value The reason to set.
     * @return This builder for chaining.
     */
    public Builder setReason(invalid_packet_reason value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000008;
      reason_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.invalid_packet_reason reason = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearReason() {
      bitField0_ = (bitField0_ & ~0x00000008);
      reason_ = 0;
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


    // @@protoc_insertion_point(builder_scope:helium.packet_verifier.invalid_packet)
  }

  // @@protoc_insertion_point(class_scope:helium.packet_verifier.invalid_packet)
  private static final invalid_packet DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new invalid_packet();
  }

  public static invalid_packet getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<invalid_packet>
      PARSER = new com.google.protobuf.AbstractParser<invalid_packet>() {
    @Override
    public invalid_packet parsePartialFrom(
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

  public static com.google.protobuf.Parser<invalid_packet> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<invalid_packet> getParserForType() {
    return PARSER;
  }

  @Override
  public invalid_packet getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

