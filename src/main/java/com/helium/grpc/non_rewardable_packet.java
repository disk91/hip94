// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service/poc_lora.proto

package com.helium.grpc;

/**
 * Protobuf type {@code helium.poc_lora.non_rewardable_packet}
 */
public final class non_rewardable_packet extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helium.poc_lora.non_rewardable_packet)
    non_rewardable_packetOrBuilder {
private static final long serialVersionUID = 0L;
  // Use non_rewardable_packet.newBuilder() to construct.
  private non_rewardable_packet(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private non_rewardable_packet() {
    reason_ = 0;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new non_rewardable_packet();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return PocLoRa.internal_static_helium_poc_lora_non_rewardable_packet_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return PocLoRa.internal_static_helium_poc_lora_non_rewardable_packet_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            non_rewardable_packet.class, Builder.class);
  }

  public static final int PACKET_FIELD_NUMBER = 1;
  private valid_packet packet_;
  /**
   * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
   * @return Whether the packet field is set.
   */
  @Override
  public boolean hasPacket() {
    return packet_ != null;
  }
  /**
   * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
   * @return The packet.
   */
  @Override
  public valid_packet getPacket() {
    return packet_ == null ? valid_packet.getDefaultInstance() : packet_;
  }
  /**
   * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
   */
  @Override
  public valid_packetOrBuilder getPacketOrBuilder() {
    return packet_ == null ? valid_packet.getDefaultInstance() : packet_;
  }

  public static final int REASON_FIELD_NUMBER = 2;
  private int reason_ = 0;
  /**
   * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
   * @return The enum numeric value on the wire for reason.
   */
  @Override public int getReasonValue() {
    return reason_;
  }
  /**
   * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
   * @return The reason.
   */
  @Override public non_rewardable_packet_reason getReason() {
    non_rewardable_packet_reason result = non_rewardable_packet_reason.forNumber(reason_);
    return result == null ? non_rewardable_packet_reason.UNRECOGNIZED : result;
  }

  public static final int TIMESTAMP_FIELD_NUMBER = 3;
  private long timestamp_ = 0L;
  /**
   * <pre>
   * timestamp in millis of when the packet was determined non rewardable
   * </pre>
   *
   * <code>uint64 timestamp = 3;</code>
   * @return The timestamp.
   */
  @Override
  public long getTimestamp() {
    return timestamp_;
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
    if (packet_ != null) {
      output.writeMessage(1, getPacket());
    }
    if (reason_ != non_rewardable_packet_reason.gateway_not_found_for_packet.getNumber()) {
      output.writeEnum(2, reason_);
    }
    if (timestamp_ != 0L) {
      output.writeUInt64(3, timestamp_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (packet_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getPacket());
    }
    if (reason_ != non_rewardable_packet_reason.gateway_not_found_for_packet.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, reason_);
    }
    if (timestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(3, timestamp_);
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
    if (!(obj instanceof non_rewardable_packet)) {
      return super.equals(obj);
    }
    non_rewardable_packet other = (non_rewardable_packet) obj;

    if (hasPacket() != other.hasPacket()) return false;
    if (hasPacket()) {
      if (!getPacket()
          .equals(other.getPacket())) return false;
    }
    if (reason_ != other.reason_) return false;
    if (getTimestamp()
        != other.getTimestamp()) return false;
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
    if (hasPacket()) {
      hash = (37 * hash) + PACKET_FIELD_NUMBER;
      hash = (53 * hash) + getPacket().hashCode();
    }
    hash = (37 * hash) + REASON_FIELD_NUMBER;
    hash = (53 * hash) + reason_;
    hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTimestamp());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static non_rewardable_packet parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static non_rewardable_packet parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static non_rewardable_packet parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static non_rewardable_packet parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static non_rewardable_packet parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static non_rewardable_packet parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static non_rewardable_packet parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static non_rewardable_packet parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static non_rewardable_packet parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static non_rewardable_packet parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static non_rewardable_packet parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static non_rewardable_packet parseFrom(
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
  public static Builder newBuilder(non_rewardable_packet prototype) {
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
   * Protobuf type {@code helium.poc_lora.non_rewardable_packet}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.poc_lora.non_rewardable_packet)
      non_rewardable_packetOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PocLoRa.internal_static_helium_poc_lora_non_rewardable_packet_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PocLoRa.internal_static_helium_poc_lora_non_rewardable_packet_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              non_rewardable_packet.class, Builder.class);
    }

    // Construct using com.helium.grpc.non_rewardable_packet.newBuilder()
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
      packet_ = null;
      if (packetBuilder_ != null) {
        packetBuilder_.dispose();
        packetBuilder_ = null;
      }
      reason_ = 0;
      timestamp_ = 0L;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return PocLoRa.internal_static_helium_poc_lora_non_rewardable_packet_descriptor;
    }

    @Override
    public non_rewardable_packet getDefaultInstanceForType() {
      return non_rewardable_packet.getDefaultInstance();
    }

    @Override
    public non_rewardable_packet build() {
      non_rewardable_packet result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public non_rewardable_packet buildPartial() {
      non_rewardable_packet result = new non_rewardable_packet(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(non_rewardable_packet result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.packet_ = packetBuilder_ == null
            ? packet_
            : packetBuilder_.build();
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.reason_ = reason_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.timestamp_ = timestamp_;
      }
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof non_rewardable_packet) {
        return mergeFrom((non_rewardable_packet)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(non_rewardable_packet other) {
      if (other == non_rewardable_packet.getDefaultInstance()) return this;
      if (other.hasPacket()) {
        mergePacket(other.getPacket());
      }
      if (other.reason_ != 0) {
        setReasonValue(other.getReasonValue());
      }
      if (other.getTimestamp() != 0L) {
        setTimestamp(other.getTimestamp());
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
              input.readMessage(
                  getPacketFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              reason_ = input.readEnum();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              timestamp_ = input.readUInt64();
              bitField0_ |= 0x00000004;
              break;
            } // case 24
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

    private valid_packet packet_;
    private com.google.protobuf.SingleFieldBuilderV3<
        valid_packet, valid_packet.Builder, valid_packetOrBuilder> packetBuilder_;
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     * @return Whether the packet field is set.
     */
    public boolean hasPacket() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     * @return The packet.
     */
    public valid_packet getPacket() {
      if (packetBuilder_ == null) {
        return packet_ == null ? valid_packet.getDefaultInstance() : packet_;
      } else {
        return packetBuilder_.getMessage();
      }
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public Builder setPacket(valid_packet value) {
      if (packetBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        packet_ = value;
      } else {
        packetBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public Builder setPacket(
        valid_packet.Builder builderForValue) {
      if (packetBuilder_ == null) {
        packet_ = builderForValue.build();
      } else {
        packetBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public Builder mergePacket(valid_packet value) {
      if (packetBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          packet_ != null &&
          packet_ != valid_packet.getDefaultInstance()) {
          getPacketBuilder().mergeFrom(value);
        } else {
          packet_ = value;
        }
      } else {
        packetBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public Builder clearPacket() {
      bitField0_ = (bitField0_ & ~0x00000001);
      packet_ = null;
      if (packetBuilder_ != null) {
        packetBuilder_.dispose();
        packetBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public valid_packet.Builder getPacketBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getPacketFieldBuilder().getBuilder();
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    public valid_packetOrBuilder getPacketOrBuilder() {
      if (packetBuilder_ != null) {
        return packetBuilder_.getMessageOrBuilder();
      } else {
        return packet_ == null ?
            valid_packet.getDefaultInstance() : packet_;
      }
    }
    /**
     * <code>.helium.packet_verifier.valid_packet packet = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        valid_packet, valid_packet.Builder, valid_packetOrBuilder>
        getPacketFieldBuilder() {
      if (packetBuilder_ == null) {
        packetBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            valid_packet, valid_packet.Builder, valid_packetOrBuilder>(
                getPacket(),
                getParentForChildren(),
                isClean());
        packet_ = null;
      }
      return packetBuilder_;
    }

    private int reason_ = 0;
    /**
     * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
     * @return The enum numeric value on the wire for reason.
     */
    @Override public int getReasonValue() {
      return reason_;
    }
    /**
     * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
     * @param value The enum numeric value on the wire for reason to set.
     * @return This builder for chaining.
     */
    public Builder setReasonValue(int value) {
      reason_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
     * @return The reason.
     */
    @Override
    public non_rewardable_packet_reason getReason() {
      non_rewardable_packet_reason result = non_rewardable_packet_reason.forNumber(reason_);
      return result == null ? non_rewardable_packet_reason.UNRECOGNIZED : result;
    }
    /**
     * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
     * @param value The reason to set.
     * @return This builder for chaining.
     */
    public Builder setReason(non_rewardable_packet_reason value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000002;
      reason_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.non_rewardable_packet_reason reason = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearReason() {
      bitField0_ = (bitField0_ & ~0x00000002);
      reason_ = 0;
      onChanged();
      return this;
    }

    private long timestamp_ ;
    /**
     * <pre>
     * timestamp in millis of when the packet was determined non rewardable
     * </pre>
     *
     * <code>uint64 timestamp = 3;</code>
     * @return The timestamp.
     */
    @Override
    public long getTimestamp() {
      return timestamp_;
    }
    /**
     * <pre>
     * timestamp in millis of when the packet was determined non rewardable
     * </pre>
     *
     * <code>uint64 timestamp = 3;</code>
     * @param value The timestamp to set.
     * @return This builder for chaining.
     */
    public Builder setTimestamp(long value) {

      timestamp_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * timestamp in millis of when the packet was determined non rewardable
     * </pre>
     *
     * <code>uint64 timestamp = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTimestamp() {
      bitField0_ = (bitField0_ & ~0x00000004);
      timestamp_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:helium.poc_lora.non_rewardable_packet)
  }

  // @@protoc_insertion_point(class_scope:helium.poc_lora.non_rewardable_packet)
  private static final non_rewardable_packet DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new non_rewardable_packet();
  }

  public static non_rewardable_packet getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<non_rewardable_packet>
      PARSER = new com.google.protobuf.AbstractParser<non_rewardable_packet>() {
    @Override
    public non_rewardable_packet parsePartialFrom(
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

  public static com.google.protobuf.Parser<non_rewardable_packet> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<non_rewardable_packet> getParserForType() {
    return PARSER;
  }

  @Override
  public non_rewardable_packet getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

