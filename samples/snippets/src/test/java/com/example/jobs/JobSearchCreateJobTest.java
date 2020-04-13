/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jobs;

import static com.google.common.truth.Truth.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JobSearchCreateJobTest {
  private static final String PROJECT_ID = System.getenv("GOOGLE_CLOUD_PROJECT");
  private static final String TENANT_ID = "50c14f00-dc38-4812-989b-d9b59c7fdf07";
  private static final String COMPANY_ID = "bdad284d-9aca-4cb9-af09-ce65afcc5d6a";
  private static final String POST_UNIQUE_ID =
      String.format(
          "TEST_POST_ID_%s",
          UUID.randomUUID().toString().substring(0, 20)); // Posting ID. Unique per job.

  private String jobId;
  private ByteArrayOutputStream bout;
  private PrintStream out;

  @Before
  public void setUp() {
    bout = new ByteArrayOutputStream();
    out = new PrintStream(bout);
    System.setOut(out);
  }

  @Test
  public void testCreateJob() throws IOException {
    // create a job.
    JobSearchCreateJob.createJob(
        PROJECT_ID, TENANT_ID, COMPANY_ID, POST_UNIQUE_ID, "http://www.jobUrl.com");
    String got = bout.toString();

    assertThat(got).contains("Created job:");
    jobId = JobSearchGetJobTest.extractLastId(got.split("\n")[0].trim());

    bout = new ByteArrayOutputStream();
    out = new PrintStream(bout);
    System.setOut(out);
  }

  @After
  public void tearDown() throws IOException {
    // delete that job.
    JobSearchDeleteJob.deleteJob(PROJECT_ID, TENANT_ID, jobId);
    String got = bout.toString();
    assertThat(got).contains("Deleted job");
    System.setOut(null);
  }
}
