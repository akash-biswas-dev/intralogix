package com.biswasakashdev.nexussphere.workspace.repository;

import org.springframework.boot.data.r2dbc.test.autoconfigure.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;

@DataR2dbcTest
@ActiveProfiles(value = {
        "test"
})
abstract class AbstractRepositoryTest {
}