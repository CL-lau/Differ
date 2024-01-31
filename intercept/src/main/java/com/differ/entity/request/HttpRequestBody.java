package com.differ.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestBody extends RequestBody {

    private long id;
    private int sequenceNumber;
    private MediaType contentType;
    @Nullable
    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        bufferedSink.write(String.valueOf(sequenceNumber).getBytes());
    }
}
