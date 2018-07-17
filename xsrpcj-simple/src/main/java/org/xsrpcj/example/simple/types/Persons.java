// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: PersonsMessageContainer.proto

package org.xsrpcj.example.simple.types;

public final class Persons {
  private Persons() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageContainerOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Persons.MessageContainer)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
     */
    int getMessageTypeValue();
    /**
     * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
     */
    org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType getMessageType();

    /**
     * <code>bytes messageData = 2;</code>
     */
    com.google.protobuf.ByteString getMessageData();
  }
  /**
   * Protobuf type {@code Persons.MessageContainer}
   */
  public  static final class MessageContainer extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Persons.MessageContainer)
      MessageContainerOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use MessageContainer.newBuilder() to construct.
    private MessageContainer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private MessageContainer() {
      messageType_ = 0;
      messageData_ = com.google.protobuf.ByteString.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private MessageContainer(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              int rawValue = input.readEnum();

              messageType_ = rawValue;
              break;
            }
            case 18: {

              messageData_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.xsrpcj.example.simple.types.Persons.internal_static_Persons_MessageContainer_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.xsrpcj.example.simple.types.Persons.internal_static_Persons_MessageContainer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.xsrpcj.example.simple.types.Persons.MessageContainer.class, org.xsrpcj.example.simple.types.Persons.MessageContainer.Builder.class);
    }

    /**
     * Protobuf enum {@code Persons.MessageContainer.MessageType}
     */
    public enum MessageType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>searchRequest = 0;</code>
       */
      searchRequest(0),
      /**
       * <code>searchResponse = 1;</code>
       */
      searchResponse(1),
      /**
       * <code>notifyRequest = 2;</code>
       */
      notifyRequest(2),
      /**
       * <code>notifyResponse = 3;</code>
       */
      notifyResponse(3),
      /**
       * <code>notifyCallback = 4;</code>
       */
      notifyCallback(4),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>searchRequest = 0;</code>
       */
      public static final int searchRequest_VALUE = 0;
      /**
       * <code>searchResponse = 1;</code>
       */
      public static final int searchResponse_VALUE = 1;
      /**
       * <code>notifyRequest = 2;</code>
       */
      public static final int notifyRequest_VALUE = 2;
      /**
       * <code>notifyResponse = 3;</code>
       */
      public static final int notifyResponse_VALUE = 3;
      /**
       * <code>notifyCallback = 4;</code>
       */
      public static final int notifyCallback_VALUE = 4;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static MessageType valueOf(int value) {
        return forNumber(value);
      }

      public static MessageType forNumber(int value) {
        switch (value) {
          case 0: return searchRequest;
          case 1: return searchResponse;
          case 2: return notifyRequest;
          case 3: return notifyResponse;
          case 4: return notifyCallback;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<MessageType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          MessageType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<MessageType>() {
              public MessageType findValueByNumber(int number) {
                return MessageType.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return org.xsrpcj.example.simple.types.Persons.MessageContainer.getDescriptor().getEnumTypes().get(0);
      }

      private static final MessageType[] VALUES = values();

      public static MessageType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new java.lang.IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private MessageType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:Persons.MessageContainer.MessageType)
    }

    public static final int MESSAGETYPE_FIELD_NUMBER = 1;
    private int messageType_;
    /**
     * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
     */
    public int getMessageTypeValue() {
      return messageType_;
    }
    /**
     * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
     */
    public org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType getMessageType() {
      org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType result = org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.valueOf(messageType_);
      return result == null ? org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.UNRECOGNIZED : result;
    }

    public static final int MESSAGEDATA_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString messageData_;
    /**
     * <code>bytes messageData = 2;</code>
     */
    public com.google.protobuf.ByteString getMessageData() {
      return messageData_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (messageType_ != org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.searchRequest.getNumber()) {
        output.writeEnum(1, messageType_);
      }
      if (!messageData_.isEmpty()) {
        output.writeBytes(2, messageData_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (messageType_ != org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.searchRequest.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(1, messageType_);
      }
      if (!messageData_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, messageData_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof org.xsrpcj.example.simple.types.Persons.MessageContainer)) {
        return super.equals(obj);
      }
      org.xsrpcj.example.simple.types.Persons.MessageContainer other = (org.xsrpcj.example.simple.types.Persons.MessageContainer) obj;

      boolean result = true;
      result = result && messageType_ == other.messageType_;
      result = result && getMessageData()
          .equals(other.getMessageData());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MESSAGETYPE_FIELD_NUMBER;
      hash = (53 * hash) + messageType_;
      hash = (37 * hash) + MESSAGEDATA_FIELD_NUMBER;
      hash = (53 * hash) + getMessageData().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static org.xsrpcj.example.simple.types.Persons.MessageContainer parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(org.xsrpcj.example.simple.types.Persons.MessageContainer prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Persons.MessageContainer}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Persons.MessageContainer)
        org.xsrpcj.example.simple.types.Persons.MessageContainerOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.xsrpcj.example.simple.types.Persons.internal_static_Persons_MessageContainer_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.xsrpcj.example.simple.types.Persons.internal_static_Persons_MessageContainer_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                org.xsrpcj.example.simple.types.Persons.MessageContainer.class, org.xsrpcj.example.simple.types.Persons.MessageContainer.Builder.class);
      }

      // Construct using org.xsrpcj.example.simple.types.Persons.MessageContainer.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        messageType_ = 0;

        messageData_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.xsrpcj.example.simple.types.Persons.internal_static_Persons_MessageContainer_descriptor;
      }

      public org.xsrpcj.example.simple.types.Persons.MessageContainer getDefaultInstanceForType() {
        return org.xsrpcj.example.simple.types.Persons.MessageContainer.getDefaultInstance();
      }

      public org.xsrpcj.example.simple.types.Persons.MessageContainer build() {
        org.xsrpcj.example.simple.types.Persons.MessageContainer result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public org.xsrpcj.example.simple.types.Persons.MessageContainer buildPartial() {
        org.xsrpcj.example.simple.types.Persons.MessageContainer result = new org.xsrpcj.example.simple.types.Persons.MessageContainer(this);
        result.messageType_ = messageType_;
        result.messageData_ = messageData_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.xsrpcj.example.simple.types.Persons.MessageContainer) {
          return mergeFrom((org.xsrpcj.example.simple.types.Persons.MessageContainer)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.xsrpcj.example.simple.types.Persons.MessageContainer other) {
        if (other == org.xsrpcj.example.simple.types.Persons.MessageContainer.getDefaultInstance()) return this;
        if (other.messageType_ != 0) {
          setMessageTypeValue(other.getMessageTypeValue());
        }
        if (other.getMessageData() != com.google.protobuf.ByteString.EMPTY) {
          setMessageData(other.getMessageData());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        org.xsrpcj.example.simple.types.Persons.MessageContainer parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.xsrpcj.example.simple.types.Persons.MessageContainer) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int messageType_ = 0;
      /**
       * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
       */
      public int getMessageTypeValue() {
        return messageType_;
      }
      /**
       * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
       */
      public Builder setMessageTypeValue(int value) {
        messageType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
       */
      public org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType getMessageType() {
        org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType result = org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.valueOf(messageType_);
        return result == null ? org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType.UNRECOGNIZED : result;
      }
      /**
       * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
       */
      public Builder setMessageType(org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType value) {
        if (value == null) {
          throw new NullPointerException();
        }
        
        messageType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.Persons.MessageContainer.MessageType messageType = 1;</code>
       */
      public Builder clearMessageType() {
        
        messageType_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString messageData_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes messageData = 2;</code>
       */
      public com.google.protobuf.ByteString getMessageData() {
        return messageData_;
      }
      /**
       * <code>bytes messageData = 2;</code>
       */
      public Builder setMessageData(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        messageData_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes messageData = 2;</code>
       */
      public Builder clearMessageData() {
        
        messageData_ = getDefaultInstance().getMessageData();
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Persons.MessageContainer)
    }

    // @@protoc_insertion_point(class_scope:Persons.MessageContainer)
    private static final org.xsrpcj.example.simple.types.Persons.MessageContainer DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new org.xsrpcj.example.simple.types.Persons.MessageContainer();
    }

    public static org.xsrpcj.example.simple.types.Persons.MessageContainer getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<MessageContainer>
        PARSER = new com.google.protobuf.AbstractParser<MessageContainer>() {
      public MessageContainer parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MessageContainer(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<MessageContainer> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<MessageContainer> getParserForType() {
      return PARSER;
    }

    public org.xsrpcj.example.simple.types.Persons.MessageContainer getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Persons_MessageContainer_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Persons_MessageContainer_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035PersonsMessageContainer.proto\022\007Persons" +
      "\"\324\001\n\020MessageContainer\022:\n\013messageType\030\001 \001" +
      "(\0162%.Persons.MessageContainer.MessageTyp" +
      "e\022\023\n\013messageData\030\002 \001(\014\"o\n\013MessageType\022\021\n" +
      "\rsearchRequest\020\000\022\022\n\016searchResponse\020\001\022\021\n\r" +
      "notifyRequest\020\002\022\022\n\016notifyResponse\020\003\022\022\n\016n" +
      "otifyCallback\020\004B*\n\037org.xsrpcj.example.si" +
      "mple.typesB\007Personsb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Persons_MessageContainer_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Persons_MessageContainer_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Persons_MessageContainer_descriptor,
        new java.lang.String[] { "MessageType", "MessageData", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
