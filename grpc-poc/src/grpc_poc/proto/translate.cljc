;;;----------------------------------------------------------------------------------
;;; Generated by protoc-gen-clojure.  DO NOT EDIT
;;;
;;; Message Implementation of package grpc_poc.proto.translate
;;;----------------------------------------------------------------------------------
(ns grpc_poc.proto.translate
  (:require [protojure.protobuf.protocol :as pb]
            [protojure.protobuf.serdes.core :as serdes.core]
            [protojure.protobuf.serdes.complex :as serdes.complex]
            [protojure.protobuf.serdes.utils :refer [tag-map]]
            [protojure.protobuf.serdes.stream :as serdes.stream]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]))

;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; Forward declarations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

(declare cis->TranslationRequest)
(declare ecis->TranslationRequest)
(declare new-TranslationRequest)
(declare cis->TranslationResponse)
(declare ecis->TranslationResponse)
(declare new-TranslationResponse)


;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; Message Implementations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

;-----------------------------------------------------------------------------
; TranslationRequest
;-----------------------------------------------------------------------------
(defrecord TranslationRequest-record [text target-language]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-String 1  {:optimize true} (:text this) os)
    (serdes.core/write-String 2  {:optimize true} (:target-language this) os))
  pb/TypeReflection
  (gettype [this]
    "grpc_poc.proto.translate.TranslationRequest"))

(s/def :grpc_poc.proto.translate.TranslationRequest/text string?)
(s/def :grpc_poc.proto.translate.TranslationRequest/target-language string?)
(s/def ::TranslationRequest-spec (s/keys :opt-un [:grpc_poc.proto.translate.TranslationRequest/text :grpc_poc.proto.translate.TranslationRequest/target-language ]))
(def TranslationRequest-defaults {:text "" :target-language "" })

(defn cis->TranslationRequest
  "CodedInputStream to TranslationRequest"
  [is]
  (->> (tag-map TranslationRequest-defaults
         (fn [tag index]
             (case index
               1 [:text (serdes.core/cis->String is)]
               2 [:target-language (serdes.core/cis->String is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->TranslationRequest-record)))

(defn ecis->TranslationRequest
  "Embedded CodedInputStream to TranslationRequest"
  [is]
  (serdes.core/cis->embedded cis->TranslationRequest is))

(defn new-TranslationRequest
  "Creates a new instance from a map, similar to map->TranslationRequest except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::TranslationRequest-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::TranslationRequest-spec init))))]}
  (-> (merge TranslationRequest-defaults init)
      (map->TranslationRequest-record)))

(defn pb->TranslationRequest
  "Protobuf to TranslationRequest"
  [input]
  (cis->TranslationRequest (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record TranslationRequest-meta {:type "grpc_poc.proto.translate.TranslationRequest" :decoder pb->TranslationRequest})

;-----------------------------------------------------------------------------
; TranslationResponse
;-----------------------------------------------------------------------------
(defrecord TranslationResponse-record [language src-text translation]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-String 1  {:optimize true} (:language this) os)
    (serdes.core/write-String 2  {:optimize true} (:src-text this) os)
    (serdes.core/write-String 3  {:optimize true} (:translation this) os))
  pb/TypeReflection
  (gettype [this]
    "grpc_poc.proto.translate.TranslationResponse"))

(s/def :grpc_poc.proto.translate.TranslationResponse/language string?)
(s/def :grpc_poc.proto.translate.TranslationResponse/src-text string?)
(s/def :grpc_poc.proto.translate.TranslationResponse/translation string?)
(s/def ::TranslationResponse-spec (s/keys :opt-un [:grpc_poc.proto.translate.TranslationResponse/language :grpc_poc.proto.translate.TranslationResponse/src-text :grpc_poc.proto.translate.TranslationResponse/translation ]))
(def TranslationResponse-defaults {:language "" :src-text "" :translation "" })

(defn cis->TranslationResponse
  "CodedInputStream to TranslationResponse"
  [is]
  (->> (tag-map TranslationResponse-defaults
         (fn [tag index]
             (case index
               1 [:language (serdes.core/cis->String is)]
               2 [:src-text (serdes.core/cis->String is)]
               3 [:translation (serdes.core/cis->String is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->TranslationResponse-record)))

(defn ecis->TranslationResponse
  "Embedded CodedInputStream to TranslationResponse"
  [is]
  (serdes.core/cis->embedded cis->TranslationResponse is))

(defn new-TranslationResponse
  "Creates a new instance from a map, similar to map->TranslationResponse except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::TranslationResponse-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::TranslationResponse-spec init))))]}
  (-> (merge TranslationResponse-defaults init)
      (map->TranslationResponse-record)))

(defn pb->TranslationResponse
  "Protobuf to TranslationResponse"
  [input]
  (cis->TranslationResponse (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record TranslationResponse-meta {:type "grpc_poc.proto.translate.TranslationResponse" :decoder pb->TranslationResponse})

