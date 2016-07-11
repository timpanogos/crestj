/*
**  Copyright (c) 2016, Cascade Computer Consulting.
**
**  Permission to use, copy, modify, and/or distribute this software for any
**  purpose with or without fee is hereby granted, provided that the above
**  copyright notice and this permission notice appear in all copies.
**
**  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
**  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
**  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
**  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
**  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
**  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
**  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
*/
package com.ccc.crest.da;

import java.util.List;

import com.ccc.tools.da.AlreadyExistsException;
import com.ccc.tools.da.NotFoundException;

@SuppressWarnings("javadoc")
public interface CrestDataAccessor extends com.ccc.tools.da.DataAccessor
{
    public void addCapsuleer(CapsuleerData userData) throws AlreadyExistsException, Exception;
    public CapsuleerData getCapsuleer(String name) throws NotFoundException, Exception;
    public void updateCapsuleer(String name, CapsuleerData userData) throws NotFoundException, Exception;
    public void deleteCapsuleer(String name) throws Exception;
    public List<CapsuleerData> listCapsuleers() throws Exception;
    public boolean hasApiKeys(String name) throws Exception;
}
