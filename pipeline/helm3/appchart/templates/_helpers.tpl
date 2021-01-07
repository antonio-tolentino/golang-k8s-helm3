{{/*
Expand the name of the chart.
*/}}
{{- define "appchart.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "appchart.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "appchart.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "appchart.labels" -}}
helm.sh/chart: {{ include "appchart.chart" . }}
{{ include "appchart.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Values.image.tag | quote }}
app: {{ .Release.Name }}
version: {{ .Values.image.tag | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "appchart.selectorLabels" -}}
app.kubernetes.io/name: {{ include "appchart.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}


{{/*
DataDog environment.
*/}}
{{- define "appchart.dataDogEnv" -}}
{{- printf  "%s-%s" .Values.dataDog.envName .Values.dataDog.dataDogUniqueID }}
{{- end }}

{{/*
DataDog service.
*/}}
{{- define "appchart.dataDogService" -}}
{{- printf  "%s-%s" (include "appchart.fullname" .) .Values.dataDog.dataDogUniqueID }}
{{- end }}

{{/*
DataDog labels
*/}}
{{- define "appchart.dataDogLabels" -}}
tags.datadoghq.com/env: {{ include "appchart.dataDogEnv" . }}
tags.datadoghq.com/service: {{ include "appchart.dataDogService" . }}
tags.datadoghq.com/version: {{ .Values.image.tag }}
{{- end }}

{{/*
DataDog variables
*/}}
{{- define "appchart.dataDogVar" -}}
{{- with .Values.dataDog.podEnvironment }}
{{- toYaml . }}
{{- end }}
{{- end }}


{{/*
Create the name of the service account to use
*/}}
{{- define "appchart.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "appchart.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Create Cluster FQDN Service name
*/}}
{{- define "appchart.serviceClusterFQDN" -}}
{{- printf "%s.%s.svc.cluster.local" (include "appchart.fullname" .) .Release.Namespace }}
{{- end }}