/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// DO NOT EDIT! This is a generated sample ("Request",  "job_search_create_client_event")
// sample-metadata:
//   title:
//   description: Creates a client event
//   usage: gradle run -PmainClass=com.google.cloud.examples.talent.v4beta1.JobSearchCreateClientEvent [--args='[--project_id "Your Google Cloud Project ID"] [--tenant_id "Your Tenant ID (using tenancy is optional)"] [--request_id "[request_id from ResponseMetadata]"] [--event_id "[Set this to a unique identifier]"]']

package com.google.cloud.examples.talent.v4beta1;

import com.google.cloud.talent.v4beta1.ClientEvent;
import com.google.cloud.talent.v4beta1.CreateClientEventRequest;
import com.google.cloud.talent.v4beta1.EventServiceClient;
import com.google.cloud.talent.v4beta1.JobEvent;
import com.google.cloud.talent.v4beta1.TenantName;
import com.google.cloud.talent.v4beta1.TenantOrProjectName;
import com.google.protobuf.Timestamp;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class JobSearchCreateClientEvent {
  // [START job_search_create_client_event]
  /*
   * Please include the following imports to run this sample.
   *
   * import com.google.cloud.talent.v4beta1.ClientEvent;
   * import com.google.cloud.talent.v4beta1.CreateClientEventRequest;
   * import com.google.cloud.talent.v4beta1.EventServiceClient;
   * import com.google.cloud.talent.v4beta1.JobEvent;
   * import com.google.cloud.talent.v4beta1.TenantName;
   * import com.google.cloud.talent.v4beta1.TenantOrProjectName;
   * import com.google.protobuf.Timestamp;
   * import java.util.Arrays;
   * import java.util.List;
   */

  public static void sampleCreateClientEvent() {
    // TODO(developer): Replace these variables before running the sample.
    String projectId = "Your Google Cloud Project ID";
    String tenantId = "Your Tenant ID (using tenancy is optional)";
    String requestId = "[request_id from ResponseMetadata]";
    String eventId = "[Set this to a unique identifier]";
    sampleCreateClientEvent(projectId, tenantId, requestId, eventId);
  }

  /**
   * Creates a client event
   *
   * @param projectId Your Google Cloud Project ID
   * @param tenantId Identifier of the Tenant
   * @param requestId A unique ID generated in the API responses. Value should be set to the
   *     request_id from an API response.
   * @param eventId A unique identifier, generated by the client application
   */
  public static void sampleCreateClientEvent(
      String projectId, String tenantId, String requestId, String eventId) {
    try (EventServiceClient eventServiceClient = EventServiceClient.create()) {
      TenantOrProjectName parent = TenantName.of(projectId, tenantId);

      // The timestamp of the event as seconds of UTC time since Unix epoch
      // For more information on how to create google.protobuf.Timestamps
      // See: https://github.com/protocolbuffers/protobuf/blob/master/src/google/protobuf/timestamp.proto
      long seconds = 0L;
      Timestamp createTime = Timestamp.newBuilder().setSeconds(seconds).build();

      // The type of event attributed to the behavior of the end user
      JobEvent.JobEventType type = JobEvent.JobEventType.VIEW;

      // List of job names associated with this event
      String jobsElement = "projects/[Project ID]/tenants/[Tenant ID]/jobs/[Job ID]";
      String jobsElement2 = "projects/[Project ID]/tenants/[Tenant ID]/jobs/[Job ID]";
      List<String> jobs = Arrays.asList(jobsElement, jobsElement2);
      JobEvent jobEvent = JobEvent.newBuilder().setType(type).addAllJobs(jobs).build();
      ClientEvent clientEvent =
          ClientEvent.newBuilder()
              .setRequestId(requestId)
              .setEventId(eventId)
              .setCreateTime(createTime)
              .setJobEvent(jobEvent)
              .build();
      CreateClientEventRequest request =
          CreateClientEventRequest.newBuilder()
              .setParent(parent.toString())
              .setClientEvent(clientEvent)
              .build();
      ClientEvent response = eventServiceClient.createClientEvent(request);
      System.out.println("Created client event");
    } catch (Exception exception) {
      System.err.println("Failed to create the client due to: " + exception);
    }
  }
  // [END job_search_create_client_event]

  public static void main(String[] args) throws Exception {
    Options options = new Options();
    options.addOption(
        Option.builder("").required(false).hasArg(true).longOpt("project_id").build());
    options.addOption(Option.builder("").required(false).hasArg(true).longOpt("tenant_id").build());
    options.addOption(
        Option.builder("").required(false).hasArg(true).longOpt("request_id").build());
    options.addOption(Option.builder("").required(false).hasArg(true).longOpt("event_id").build());

    CommandLine cl = (new DefaultParser()).parse(options, args);
    String projectId = cl.getOptionValue("project_id", "Your Google Cloud Project ID");
    String tenantId = cl.getOptionValue("tenant_id", "Your Tenant ID (using tenancy is optional)");
    String requestId = cl.getOptionValue("request_id", "[request_id from ResponseMetadata]");
    String eventId = cl.getOptionValue("event_id", "[Set this to a unique identifier]");

    sampleCreateClientEvent(projectId, tenantId, requestId, eventId);
  }
}
