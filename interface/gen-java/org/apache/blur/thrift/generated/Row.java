/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package org.apache.blur.thrift.generated;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rows contain a list of records.
 */
public class Row implements org.apache.thrift.TBase<Row, Row._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Row");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RECORDS_FIELD_DESC = new org.apache.thrift.protocol.TField("records", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField RECORD_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("recordCount", org.apache.thrift.protocol.TType.I32, (short)3);

  /**
   * The row id.
   */
  public String id; // required
  /**
   * The list records within the row.  If paging is used this list will only
   * reflect the paged records from the selector.
   */
  public List<Record> records; // required
  /**
   * The total record count for the row.  If paging is used in a selector to page
   * through records of a row, this count will reflect the entire row.
   */
  public int recordCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * The row id.
     */
    ID((short)1, "id"),
    /**
     * The list records within the row.  If paging is used this list will only
     * reflect the paged records from the selector.
     */
    RECORDS((short)2, "records"),
    /**
     * The total record count for the row.  If paging is used in a selector to page
     * through records of a row, this count will reflect the entire row.
     */
    RECORD_COUNT((short)3, "recordCount");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // RECORDS
          return RECORDS;
        case 3: // RECORD_COUNT
          return RECORD_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __RECORDCOUNT_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RECORDS, new org.apache.thrift.meta_data.FieldMetaData("records", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Record.class))));
    tmpMap.put(_Fields.RECORD_COUNT, new org.apache.thrift.meta_data.FieldMetaData("recordCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Row.class, metaDataMap);
  }

  public Row() {
  }

  public Row(
    String id,
    List<Record> records,
    int recordCount)
  {
    this();
    this.id = id;
    this.records = records;
    this.recordCount = recordCount;
    setRecordCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Row(Row other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetRecords()) {
      List<Record> __this__records = new ArrayList<Record>();
      for (Record other_element : other.records) {
        __this__records.add(new Record(other_element));
      }
      this.records = __this__records;
    }
    this.recordCount = other.recordCount;
  }

  public Row deepCopy() {
    return new Row(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.records = null;
    setRecordCountIsSet(false);
    this.recordCount = 0;
  }

  /**
   * The row id.
   */
  public String getId() {
    return this.id;
  }

  /**
   * The row id.
   */
  public Row setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public int getRecordsSize() {
    return (this.records == null) ? 0 : this.records.size();
  }

  public java.util.Iterator<Record> getRecordsIterator() {
    return (this.records == null) ? null : this.records.iterator();
  }

  public void addToRecords(Record elem) {
    if (this.records == null) {
      this.records = new ArrayList<Record>();
    }
    this.records.add(elem);
  }

  /**
   * The list records within the row.  If paging is used this list will only
   * reflect the paged records from the selector.
   */
  public List<Record> getRecords() {
    return this.records;
  }

  /**
   * The list records within the row.  If paging is used this list will only
   * reflect the paged records from the selector.
   */
  public Row setRecords(List<Record> records) {
    this.records = records;
    return this;
  }

  public void unsetRecords() {
    this.records = null;
  }

  /** Returns true if field records is set (has been assigned a value) and false otherwise */
  public boolean isSetRecords() {
    return this.records != null;
  }

  public void setRecordsIsSet(boolean value) {
    if (!value) {
      this.records = null;
    }
  }

  /**
   * The total record count for the row.  If paging is used in a selector to page
   * through records of a row, this count will reflect the entire row.
   */
  public int getRecordCount() {
    return this.recordCount;
  }

  /**
   * The total record count for the row.  If paging is used in a selector to page
   * through records of a row, this count will reflect the entire row.
   */
  public Row setRecordCount(int recordCount) {
    this.recordCount = recordCount;
    setRecordCountIsSet(true);
    return this;
  }

  public void unsetRecordCount() {
    __isset_bit_vector.clear(__RECORDCOUNT_ISSET_ID);
  }

  /** Returns true if field recordCount is set (has been assigned a value) and false otherwise */
  public boolean isSetRecordCount() {
    return __isset_bit_vector.get(__RECORDCOUNT_ISSET_ID);
  }

  public void setRecordCountIsSet(boolean value) {
    __isset_bit_vector.set(__RECORDCOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case RECORDS:
      if (value == null) {
        unsetRecords();
      } else {
        setRecords((List<Record>)value);
      }
      break;

    case RECORD_COUNT:
      if (value == null) {
        unsetRecordCount();
      } else {
        setRecordCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case RECORDS:
      return getRecords();

    case RECORD_COUNT:
      return Integer.valueOf(getRecordCount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case RECORDS:
      return isSetRecords();
    case RECORD_COUNT:
      return isSetRecordCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Row)
      return this.equals((Row)that);
    return false;
  }

  public boolean equals(Row that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_records = true && this.isSetRecords();
    boolean that_present_records = true && that.isSetRecords();
    if (this_present_records || that_present_records) {
      if (!(this_present_records && that_present_records))
        return false;
      if (!this.records.equals(that.records))
        return false;
    }

    boolean this_present_recordCount = true;
    boolean that_present_recordCount = true;
    if (this_present_recordCount || that_present_recordCount) {
      if (!(this_present_recordCount && that_present_recordCount))
        return false;
      if (this.recordCount != that.recordCount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Row other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Row typedOther = (Row)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecords()).compareTo(typedOther.isSetRecords());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecords()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.records, typedOther.records);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecordCount()).compareTo(typedOther.isSetRecordCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecordCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recordCount, typedOther.recordCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // ID
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.id = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // RECORDS
          if (field.type == org.apache.thrift.protocol.TType.LIST) {
            {
              org.apache.thrift.protocol.TList _list4 = iprot.readListBegin();
              this.records = new ArrayList<Record>(_list4.size);
              for (int _i5 = 0; _i5 < _list4.size; ++_i5)
              {
                Record _elem6; // required
                _elem6 = new Record();
                _elem6.read(iprot);
                this.records.add(_elem6);
              }
              iprot.readListEnd();
            }
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // RECORD_COUNT
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.recordCount = iprot.readI32();
            setRecordCountIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.id != null) {
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeString(this.id);
      oprot.writeFieldEnd();
    }
    if (this.records != null) {
      oprot.writeFieldBegin(RECORDS_FIELD_DESC);
      {
        oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, this.records.size()));
        for (Record _iter7 : this.records)
        {
          _iter7.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(RECORD_COUNT_FIELD_DESC);
    oprot.writeI32(this.recordCount);
    oprot.writeFieldEnd();
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Row(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("records:");
    if (this.records == null) {
      sb.append("null");
    } else {
      sb.append(this.records);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("recordCount:");
    sb.append(this.recordCount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

