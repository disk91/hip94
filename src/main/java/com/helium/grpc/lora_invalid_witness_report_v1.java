// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service/poc_lora.proto

package com.helium.grpc;

/**
 * <pre>
 * tagged invalid witness report produced by the verifier
 * </pre>
 *
 * Protobuf type {@code helium.poc_lora.lora_invalid_witness_report_v1}
 */
public final class lora_invalid_witness_report_v1 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helium.poc_lora.lora_invalid_witness_report_v1)
    lora_invalid_witness_report_v1OrBuilder {
private static final long serialVersionUID = 0L;
  // Use lora_invalid_witness_report_v1.newBuilder() to construct.
  private lora_invalid_witness_report_v1(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private lora_invalid_witness_report_v1() {
    reason_ = 0;
    participantSide_ = 0;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new lora_invalid_witness_report_v1();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return PocLoRa.internal_static_helium_poc_lora_lora_invalid_witness_report_v1_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return PocLoRa.internal_static_helium_poc_lora_lora_invalid_witness_report_v1_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            lora_invalid_witness_report_v1.class, Builder.class);
  }

  public static final int RECEIVED_TIMESTAMP_FIELD_NUMBER = 1;
  private long receivedTimestamp_ = 0L;
  /**
   * <pre>
   * Timestamp at ingest in millis since unix epoch
   * </pre>
   *
   * <code>uint64 received_timestamp = 1;</code>
   * @return The receivedTimestamp.
   */
  @Override
  public long getReceivedTimestamp() {
    return receivedTimestamp_;
  }

  public static final int REASON_FIELD_NUMBER = 2;
  private int reason_ = 0;
  /**
   * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
   * @return The enum numeric value on the wire for reason.
   */
  @Override public int getReasonValue() {
    return reason_;
  }
  /**
   * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
   * @return The reason.
   */
  @Override public invalid_reason getReason() {
    invalid_reason result = invalid_reason.forNumber(reason_);
    return result == null ? invalid_reason.UNRECOGNIZED : result;
  }

  public static final int REPORT_FIELD_NUMBER = 3;
  private lora_witness_report_req_v1 report_;
  /**
   * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
   * @return Whether the report field is set.
   */
  @Override
  public boolean hasReport() {
    return report_ != null;
  }
  /**
   * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
   * @return The report.
   */
  @Override
  public lora_witness_report_req_v1 getReport() {
    return report_ == null ? lora_witness_report_req_v1.getDefaultInstance() : report_;
  }
  /**
   * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
   */
  @Override
  public lora_witness_report_req_v1OrBuilder getReportOrBuilder() {
    return report_ == null ? lora_witness_report_req_v1.getDefaultInstance() : report_;
  }

  public static final int PARTICIPANT_SIDE_FIELD_NUMBER = 4;
  private int participantSide_ = 0;
  /**
   * <pre>
   * the participant to which the reason applies,
   * which rendered the report as invalid
   * </pre>
   *
   * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
   * @return The enum numeric value on the wire for participantSide.
   */
  @Override public int getParticipantSideValue() {
    return participantSide_;
  }
  /**
   * <pre>
   * the participant to which the reason applies,
   * which rendered the report as invalid
   * </pre>
   *
   * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
   * @return The participantSide.
   */
  @Override public invalid_participant_side getParticipantSide() {
    invalid_participant_side result = invalid_participant_side.forNumber(participantSide_);
    return result == null ? invalid_participant_side.UNRECOGNIZED : result;
  }

  public static final int INVALID_DETAILS_FIELD_NUMBER = 5;
  private invalid_details invalidDetails_;
  /**
   * <pre>
   * provides any additional context for invalid reason
   * for example the deny list version used as part of the deny list check
   * </pre>
   *
   * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
   * @return Whether the invalidDetails field is set.
   */
  @Override
  public boolean hasInvalidDetails() {
    return invalidDetails_ != null;
  }
  /**
   * <pre>
   * provides any additional context for invalid reason
   * for example the deny list version used as part of the deny list check
   * </pre>
   *
   * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
   * @return The invalidDetails.
   */
  @Override
  public invalid_details getInvalidDetails() {
    return invalidDetails_ == null ? invalid_details.getDefaultInstance() : invalidDetails_;
  }
  /**
   * <pre>
   * provides any additional context for invalid reason
   * for example the deny list version used as part of the deny list check
   * </pre>
   *
   * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
   */
  @Override
  public invalid_detailsOrBuilder getInvalidDetailsOrBuilder() {
    return invalidDetails_ == null ? invalid_details.getDefaultInstance() : invalidDetails_;
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
    if (receivedTimestamp_ != 0L) {
      output.writeUInt64(1, receivedTimestamp_);
    }
    if (reason_ != invalid_reason.reason_none.getNumber()) {
      output.writeEnum(2, reason_);
    }
    if (report_ != null) {
      output.writeMessage(3, getReport());
    }
    if (participantSide_ != invalid_participant_side.side_none.getNumber()) {
      output.writeEnum(4, participantSide_);
    }
    if (invalidDetails_ != null) {
      output.writeMessage(5, getInvalidDetails());
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (receivedTimestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, receivedTimestamp_);
    }
    if (reason_ != invalid_reason.reason_none.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, reason_);
    }
    if (report_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(3, getReport());
    }
    if (participantSide_ != invalid_participant_side.side_none.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, participantSide_);
    }
    if (invalidDetails_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(5, getInvalidDetails());
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
    if (!(obj instanceof lora_invalid_witness_report_v1)) {
      return super.equals(obj);
    }
    lora_invalid_witness_report_v1 other = (lora_invalid_witness_report_v1) obj;

    if (getReceivedTimestamp()
        != other.getReceivedTimestamp()) return false;
    if (reason_ != other.reason_) return false;
    if (hasReport() != other.hasReport()) return false;
    if (hasReport()) {
      if (!getReport()
          .equals(other.getReport())) return false;
    }
    if (participantSide_ != other.participantSide_) return false;
    if (hasInvalidDetails() != other.hasInvalidDetails()) return false;
    if (hasInvalidDetails()) {
      if (!getInvalidDetails()
          .equals(other.getInvalidDetails())) return false;
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
    hash = (37 * hash) + RECEIVED_TIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getReceivedTimestamp());
    hash = (37 * hash) + REASON_FIELD_NUMBER;
    hash = (53 * hash) + reason_;
    if (hasReport()) {
      hash = (37 * hash) + REPORT_FIELD_NUMBER;
      hash = (53 * hash) + getReport().hashCode();
    }
    hash = (37 * hash) + PARTICIPANT_SIDE_FIELD_NUMBER;
    hash = (53 * hash) + participantSide_;
    if (hasInvalidDetails()) {
      hash = (37 * hash) + INVALID_DETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getInvalidDetails().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static lora_invalid_witness_report_v1 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static lora_invalid_witness_report_v1 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static lora_invalid_witness_report_v1 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static lora_invalid_witness_report_v1 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static lora_invalid_witness_report_v1 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static lora_invalid_witness_report_v1 parseFrom(
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
  public static Builder newBuilder(lora_invalid_witness_report_v1 prototype) {
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
   * <pre>
   * tagged invalid witness report produced by the verifier
   * </pre>
   *
   * Protobuf type {@code helium.poc_lora.lora_invalid_witness_report_v1}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helium.poc_lora.lora_invalid_witness_report_v1)
      lora_invalid_witness_report_v1OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return PocLoRa.internal_static_helium_poc_lora_lora_invalid_witness_report_v1_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return PocLoRa.internal_static_helium_poc_lora_lora_invalid_witness_report_v1_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              lora_invalid_witness_report_v1.class, Builder.class);
    }

    // Construct using com.helium.grpc.lora_invalid_witness_report_v1.newBuilder()
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
      receivedTimestamp_ = 0L;
      reason_ = 0;
      report_ = null;
      if (reportBuilder_ != null) {
        reportBuilder_.dispose();
        reportBuilder_ = null;
      }
      participantSide_ = 0;
      invalidDetails_ = null;
      if (invalidDetailsBuilder_ != null) {
        invalidDetailsBuilder_.dispose();
        invalidDetailsBuilder_ = null;
      }
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return PocLoRa.internal_static_helium_poc_lora_lora_invalid_witness_report_v1_descriptor;
    }

    @Override
    public lora_invalid_witness_report_v1 getDefaultInstanceForType() {
      return lora_invalid_witness_report_v1.getDefaultInstance();
    }

    @Override
    public lora_invalid_witness_report_v1 build() {
      lora_invalid_witness_report_v1 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public lora_invalid_witness_report_v1 buildPartial() {
      lora_invalid_witness_report_v1 result = new lora_invalid_witness_report_v1(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(lora_invalid_witness_report_v1 result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.receivedTimestamp_ = receivedTimestamp_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.reason_ = reason_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.report_ = reportBuilder_ == null
            ? report_
            : reportBuilder_.build();
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.participantSide_ = participantSide_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.invalidDetails_ = invalidDetailsBuilder_ == null
            ? invalidDetails_
            : invalidDetailsBuilder_.build();
      }
    }

    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof lora_invalid_witness_report_v1) {
        return mergeFrom((lora_invalid_witness_report_v1)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(lora_invalid_witness_report_v1 other) {
      if (other == lora_invalid_witness_report_v1.getDefaultInstance()) return this;
      if (other.getReceivedTimestamp() != 0L) {
        setReceivedTimestamp(other.getReceivedTimestamp());
      }
      if (other.reason_ != 0) {
        setReasonValue(other.getReasonValue());
      }
      if (other.hasReport()) {
        mergeReport(other.getReport());
      }
      if (other.participantSide_ != 0) {
        setParticipantSideValue(other.getParticipantSideValue());
      }
      if (other.hasInvalidDetails()) {
        mergeInvalidDetails(other.getInvalidDetails());
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
              receivedTimestamp_ = input.readUInt64();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              reason_ = input.readEnum();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              input.readMessage(
                  getReportFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 32: {
              participantSide_ = input.readEnum();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            case 42: {
              input.readMessage(
                  getInvalidDetailsFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000010;
              break;
            } // case 42
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

    private long receivedTimestamp_ ;
    /**
     * <pre>
     * Timestamp at ingest in millis since unix epoch
     * </pre>
     *
     * <code>uint64 received_timestamp = 1;</code>
     * @return The receivedTimestamp.
     */
    @Override
    public long getReceivedTimestamp() {
      return receivedTimestamp_;
    }
    /**
     * <pre>
     * Timestamp at ingest in millis since unix epoch
     * </pre>
     *
     * <code>uint64 received_timestamp = 1;</code>
     * @param value The receivedTimestamp to set.
     * @return This builder for chaining.
     */
    public Builder setReceivedTimestamp(long value) {

      receivedTimestamp_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Timestamp at ingest in millis since unix epoch
     * </pre>
     *
     * <code>uint64 received_timestamp = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearReceivedTimestamp() {
      bitField0_ = (bitField0_ & ~0x00000001);
      receivedTimestamp_ = 0L;
      onChanged();
      return this;
    }

    private int reason_ = 0;
    /**
     * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
     * @return The enum numeric value on the wire for reason.
     */
    @Override public int getReasonValue() {
      return reason_;
    }
    /**
     * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
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
     * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
     * @return The reason.
     */
    @Override
    public invalid_reason getReason() {
      invalid_reason result = invalid_reason.forNumber(reason_);
      return result == null ? invalid_reason.UNRECOGNIZED : result;
    }
    /**
     * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
     * @param value The reason to set.
     * @return This builder for chaining.
     */
    public Builder setReason(invalid_reason value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000002;
      reason_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.invalid_reason reason = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearReason() {
      bitField0_ = (bitField0_ & ~0x00000002);
      reason_ = 0;
      onChanged();
      return this;
    }

    private lora_witness_report_req_v1 report_;
    private com.google.protobuf.SingleFieldBuilderV3<
        lora_witness_report_req_v1, lora_witness_report_req_v1.Builder, lora_witness_report_req_v1OrBuilder> reportBuilder_;
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     * @return Whether the report field is set.
     */
    public boolean hasReport() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     * @return The report.
     */
    public lora_witness_report_req_v1 getReport() {
      if (reportBuilder_ == null) {
        return report_ == null ? lora_witness_report_req_v1.getDefaultInstance() : report_;
      } else {
        return reportBuilder_.getMessage();
      }
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public Builder setReport(lora_witness_report_req_v1 value) {
      if (reportBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        report_ = value;
      } else {
        reportBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public Builder setReport(
        lora_witness_report_req_v1.Builder builderForValue) {
      if (reportBuilder_ == null) {
        report_ = builderForValue.build();
      } else {
        reportBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public Builder mergeReport(lora_witness_report_req_v1 value) {
      if (reportBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0) &&
          report_ != null &&
          report_ != lora_witness_report_req_v1.getDefaultInstance()) {
          getReportBuilder().mergeFrom(value);
        } else {
          report_ = value;
        }
      } else {
        reportBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public Builder clearReport() {
      bitField0_ = (bitField0_ & ~0x00000004);
      report_ = null;
      if (reportBuilder_ != null) {
        reportBuilder_.dispose();
        reportBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public lora_witness_report_req_v1.Builder getReportBuilder() {
      bitField0_ |= 0x00000004;
      onChanged();
      return getReportFieldBuilder().getBuilder();
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    public lora_witness_report_req_v1OrBuilder getReportOrBuilder() {
      if (reportBuilder_ != null) {
        return reportBuilder_.getMessageOrBuilder();
      } else {
        return report_ == null ?
            lora_witness_report_req_v1.getDefaultInstance() : report_;
      }
    }
    /**
     * <code>.helium.poc_lora.lora_witness_report_req_v1 report = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        lora_witness_report_req_v1, lora_witness_report_req_v1.Builder, lora_witness_report_req_v1OrBuilder>
        getReportFieldBuilder() {
      if (reportBuilder_ == null) {
        reportBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            lora_witness_report_req_v1, lora_witness_report_req_v1.Builder, lora_witness_report_req_v1OrBuilder>(
                getReport(),
                getParentForChildren(),
                isClean());
        report_ = null;
      }
      return reportBuilder_;
    }

    private int participantSide_ = 0;
    /**
     * <pre>
     * the participant to which the reason applies,
     * which rendered the report as invalid
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
     * @return The enum numeric value on the wire for participantSide.
     */
    @Override public int getParticipantSideValue() {
      return participantSide_;
    }
    /**
     * <pre>
     * the participant to which the reason applies,
     * which rendered the report as invalid
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
     * @param value The enum numeric value on the wire for participantSide to set.
     * @return This builder for chaining.
     */
    public Builder setParticipantSideValue(int value) {
      participantSide_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * the participant to which the reason applies,
     * which rendered the report as invalid
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
     * @return The participantSide.
     */
    @Override
    public invalid_participant_side getParticipantSide() {
      invalid_participant_side result = invalid_participant_side.forNumber(participantSide_);
      return result == null ? invalid_participant_side.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * the participant to which the reason applies,
     * which rendered the report as invalid
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
     * @param value The participantSide to set.
     * @return This builder for chaining.
     */
    public Builder setParticipantSide(invalid_participant_side value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000008;
      participantSide_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * the participant to which the reason applies,
     * which rendered the report as invalid
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_participant_side participant_side = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearParticipantSide() {
      bitField0_ = (bitField0_ & ~0x00000008);
      participantSide_ = 0;
      onChanged();
      return this;
    }

    private invalid_details invalidDetails_;
    private com.google.protobuf.SingleFieldBuilderV3<
        invalid_details, invalid_details.Builder, invalid_detailsOrBuilder> invalidDetailsBuilder_;
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     * @return Whether the invalidDetails field is set.
     */
    public boolean hasInvalidDetails() {
      return ((bitField0_ & 0x00000010) != 0);
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     * @return The invalidDetails.
     */
    public invalid_details getInvalidDetails() {
      if (invalidDetailsBuilder_ == null) {
        return invalidDetails_ == null ? invalid_details.getDefaultInstance() : invalidDetails_;
      } else {
        return invalidDetailsBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public Builder setInvalidDetails(invalid_details value) {
      if (invalidDetailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        invalidDetails_ = value;
      } else {
        invalidDetailsBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public Builder setInvalidDetails(
        invalid_details.Builder builderForValue) {
      if (invalidDetailsBuilder_ == null) {
        invalidDetails_ = builderForValue.build();
      } else {
        invalidDetailsBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public Builder mergeInvalidDetails(invalid_details value) {
      if (invalidDetailsBuilder_ == null) {
        if (((bitField0_ & 0x00000010) != 0) &&
          invalidDetails_ != null &&
          invalidDetails_ != invalid_details.getDefaultInstance()) {
          getInvalidDetailsBuilder().mergeFrom(value);
        } else {
          invalidDetails_ = value;
        }
      } else {
        invalidDetailsBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public Builder clearInvalidDetails() {
      bitField0_ = (bitField0_ & ~0x00000010);
      invalidDetails_ = null;
      if (invalidDetailsBuilder_ != null) {
        invalidDetailsBuilder_.dispose();
        invalidDetailsBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public invalid_details.Builder getInvalidDetailsBuilder() {
      bitField0_ |= 0x00000010;
      onChanged();
      return getInvalidDetailsFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    public invalid_detailsOrBuilder getInvalidDetailsOrBuilder() {
      if (invalidDetailsBuilder_ != null) {
        return invalidDetailsBuilder_.getMessageOrBuilder();
      } else {
        return invalidDetails_ == null ?
            invalid_details.getDefaultInstance() : invalidDetails_;
      }
    }
    /**
     * <pre>
     * provides any additional context for invalid reason
     * for example the deny list version used as part of the deny list check
     * </pre>
     *
     * <code>.helium.poc_lora.invalid_details invalid_details = 5;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        invalid_details, invalid_details.Builder, invalid_detailsOrBuilder>
        getInvalidDetailsFieldBuilder() {
      if (invalidDetailsBuilder_ == null) {
        invalidDetailsBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            invalid_details, invalid_details.Builder, invalid_detailsOrBuilder>(
                getInvalidDetails(),
                getParentForChildren(),
                isClean());
        invalidDetails_ = null;
      }
      return invalidDetailsBuilder_;
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


    // @@protoc_insertion_point(builder_scope:helium.poc_lora.lora_invalid_witness_report_v1)
  }

  // @@protoc_insertion_point(class_scope:helium.poc_lora.lora_invalid_witness_report_v1)
  private static final lora_invalid_witness_report_v1 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new lora_invalid_witness_report_v1();
  }

  public static lora_invalid_witness_report_v1 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<lora_invalid_witness_report_v1>
      PARSER = new com.google.protobuf.AbstractParser<lora_invalid_witness_report_v1>() {
    @Override
    public lora_invalid_witness_report_v1 parsePartialFrom(
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

  public static com.google.protobuf.Parser<lora_invalid_witness_report_v1> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<lora_invalid_witness_report_v1> getParserForType() {
    return PARSER;
  }

  @Override
  public lora_invalid_witness_report_v1 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

