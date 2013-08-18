#!/usr/bin/env bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.



rm ../../../../../blur-thrift/src/main/java/org/apache/blur/thrift/generated/*
rm -r gen-java/ gen-perl/ gen-rb/ gen-html/
thrift --gen html --gen perl --gen java --gen rb --gen js Blur.thrift
for f in gen-java/org/apache/blur/thrift/generated/*.java; do
  awk -v f="apache.header" 'BEGIN {while (getline < f) txt=txt $0 "\n"} /package org\.apache\.blur\.thrift\.generated;/ {sub("package org.apache.blur.thrift.generated;", txt)} 1' $f > $f.new1
  sed 's/org\.apache\.thrift\./org\.apache\.blur\.thirdparty\.thrift_0_9_0\./g' $f.new1 > $f.new2
  sed 's/import\ org\.slf4j\.Logger/\/\/import\ org\.slf4j\.Logger/g' $f.new2 > $f.new3
  sed 's/private\ static\ final\ Logger\ LOGGER/\/\/private\ static\ final\ Logger\ LOGGER/g' $f.new3 > $f.new4
  rm $f.new1 $f.new2 $f.new3 $f
  mv $f.new4 $f
done
java -cp ../../../../../blur-util/target/blur-util-*.jar org.apache.blur.doc.CreateBlurApiHtmlPage gen-html/Blur.html ../../../../../docs/Blur.html
cp -r gen-java/* ../../../../../blur-thrift/src/main/java/
